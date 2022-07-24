(function(factory) {
  if (typeof define === "function" && define.amd) {
    define(["jquery"], factory);
  } else if (typeof exports === 'object') {
    factory(require('jquery'));
  } else {
    factory(jQuery);
  }
}(function($, undefined) {


  /**
   * Constructor function
   */
  function MyUploader(element, options, callbacks) {
    $(element).data('myuploader', this);
    this.isInit = true;
    this.itemsArray = [];
    this.$element = $(element);
    this.$container = this.$element.parent().next(options.container).length ? this.$element.parent().next(options.container) : this.$element.parent();
    this.callbacks = $.extend(true, {}, defaultCallbacks, callbacks);
    this.build(options, callbacks);
  }

  MyUploader.prototype = {
    constructor: MyUploader,

    build: function(options, callbacks) {
      var self = this;

      defaults.pick = {
        id: self.$element.attr('id') ? '#' + self.$element.attr('id') : $element
      };
      defaults.container = self.$element;
      // defaults.paste = self.$element.get(0);
      self.options = $.extend(true, {}, defaults, options);
      // 添加的文件数量
      self.fileCount = 0;
      // 添加的文件总大小
      self.fileSize = 0;
      self.callbacks.initUploaderCallback(self);
      self.callbacks.initUploaderImgCallback(self);

      // 优化retina, 在retina下这个值是2
      self.ratio = window.devicePixelRatio || 1;
      // 可能有pedding, ready, uploading, confirm, done.
      self.state = 'pedding';
      self.stateInfo = '';
      // 所有文件的进度信息，key为file id
      self.percentages = {};

      self.setAsCover = false; //是否添加设置为封面按钮

      if (self.$element.data('cover')) {
        self.setAsCover = true;
      }

      self.uploader = WebUploader.create(self.options);

      self.uploader.onUploadProgress = function(file, percentage) {
        // var $li = $('#' + file.id),
        //   $percent = $li.find('.mu-progress span');

        // $percent.css('width', percentage * 100 + '%');
        // self.percentages[file.id][1] = percentage;
        // updateTotalProgress();
      };

      self.uploader.onFileQueued = function(file) {
        self.fileCount++;
        self.fileSize += file.size;

        if (self.fileCount === 1) {
          // $placeHolder.addClass('element-invisible');
          // $statusBar.show();
        }

        self.addFile(file);
        // setState('ready');
        // updateTotalProgress();
        self.stateInfo = '正在上传中...';
        self.setState(file, 'info');
      };

      self.uploader.onFileDequeued = function(file) {
        self.fileCount--;
        self.fileSize -= file.size;

        if (!self.fileCount) {
          // setState('pedding');
        }

        self.removeFile(file);
        // updateTotalProgress();
      };

      self.uploader.onUploadSuccess = function(file, res) {
        if (res && res._raw) {
          if (res._raw.indexOf('callback') > -1) {
            var callback = function(data) {
              if (data.code && data.code == 200 && data.data && data.data.imgUrl) {
                $('#' + file.id).append($('<input type="hidden" name="imgUrl[]" value="' + data.data.imgUrl + '">'));
                $('#' + file.id).find(img).attr('src', data.data.imgUrl);
              }
            };

            eval(res._raw);
          }
        }
        if (self.callbacks.uploadSuccessCallback) {
          self.callbacks.uploadSuccessCallback(file, res);
        }

        self.stateInfo = '上传成功';
        self.setState(file, 'success');
      };

      self.uploader.onUploadError = function(file, res) {
        if (self.callbacks.uploadErrorCallback) {
          self.callbacks.uploadErrorCallback(file, res);
        }

        self.stateInfo = '上传失败';
        self.setState(file, 'error');
      };

      self.uploader.on('beforeFileQueued', function(file) {
        var max = parseInt(self.options.fileNumLimit, 10);
        if (self.fileCount >= max) {
          this.trigger('error', 'Q_EXCEED_NUM_LIMIT', max, file);
        }

        return self.fileCount >= max ? false : true;
      });

      self.uploader.onError = function(error_msg, max, file) {
        var message = "";
        if (error_msg == "Q_EXCEED_NUM_LIMIT") {
          message = "最多允许上传" + max + "个文件";
        } else if (error_msg == "Q_EXCEED_SIZE_LIMIT") {
          message = "文件总大小'" + max / (1024 * 1024) + "M'大小超出限制";
        } else if (error_msg == "F_EXCEED_SIZE") {
          message = "【" + file.name + "】文件大小超出大小'" + max / (1024 * 1024) + "M'限制";
        } else if (error_msg == "Q_TYPE_DENIED") {
          message = "上传文件类型错误";
        } else if (error_msg == "F_DUPLICATE") {
          message = "文件不能重复上传";
        }
        if (window.swal) {
          swal({
            title: "提示",
            text: message,
            showCancelButton: false,
            confirmButtonText: '确认',
            confirmButtonColor: "#D01219",
            closeOnConfirm: false
          });
        } else {
          alert(message);
        }

        return false;
      };
    },
    addFile: function(file) {
      var self = this;
      var $div = $('<div id="' + file.id + '" class="mu-thumbnail-item">' +
          '<p class="mu-title">' + file.name + '</p>' +
          '<p class="mu-img-wrap"></p>' +
          '<p class="mu-progress"><span></span></p>' +
          '</div>'),
        $btns = $('<div class="file-panel">' +
          '<a class="mu-remove">删除</a>' +
          '<a class="mu-preview">预览</a></div>').appendTo($div),
        $prgress = $div.find('p.mu-progress span'),
        $imgWrap = $div.find('p.mu-img-wrap'),
        $info = $('<p class="my-error"></p>').appendTo($div);


      if (self.setAsCover) {
        var $coverWrap = $('<p class="file-set-cover"><a href="javascript:void(0);">设置为封面</a></p>').appendTo($div);
        $coverWrap.find('a').click(function(e) {
          e.preventDefault();
          self.$element.nextAll().find('.file-set-cover').removeClass('is-cover');
          $(this).parent().addClass('is-cover');
          if (self.setAsCoverCallback) {
            self.setAsCoverCallback(file);
          }
        });
      }

      if (file.getStatus() === 'invalid') {
        // showError(file.statusText);
      } else {
        // @todo lazyload
        $imgWrap.text('预览中');
        self.uploader.makeThumb(file, function(error, src) {
          if (error) {
            $imgWrap.text('不能预览');
            return;
          }

          var img = $('<img src="' + src + '">');
          $imgWrap.empty().append(img);
        }, self.options.thumbnailWidth, self.options.thumbnailHeight);

        self.percentages[file.id] = [file.size, 0];
        file.rotation = 0;
      }

      $btns.on('click', 'a', function() {
        if ($(this).hasClass('mu-remove')) {
          self.uploader.removeFile(file);
        }

        if ($(this).hasClass('mu-preview')) {
          self.preview($(this));
        }
      });
      $div.appendTo(self.$container);
      self.callbacks.addFileCallback(file);
    },
    removeFile: function(file) {
      var self = this;
      var res = self.callbacks.removeFileCallback(file);
      var $div = $('#' + file.id);
      $div.off().find('.file-panel').off().end().remove();
    },

    setState: function(file, type) {
      var $info = $('#' + file.id).find('.my-error');
      if (type) {
        $info.addClass(type);
      }
      $info.html(this.stateInfo);
    },

    createThumb: function(imgUrl, isDelete, callback, callback2, callback3) {
      var self = this;
      self.fileCount++;
      if (typeof isDelete == "function") {
        callback3 = callback2;
        callback2 = callback;
        callback = isDelete;
        isDelete = true;
      } else if (typeof isDelete == "boolen") {

      }
      var $div = $('<div class="mu-thumbnail-item">' +
          '<p class="mu-title"></p>' +
          '<p class="mu-img-wrap"><img src="' + imgUrl + '"></p>' +
          '<p class="mu-progress"><span></span></p>' +
          '</div>'),
        $prgress = $div.find('p.mu-progress span'),
        $imgWrap = $div.find('p.mu-img-wrap'),
        $info = $('<p class="my-error"></p>');
      var btnStr = '<div class="file-panel">';
      if (isDelete) {
        btnStr += '<a class="mu-remove">删除</a>';
        btnStr += '<a class="mu-preview">预览</a></div>';
      } else {
        btnStr += '<a class="mu-preview" style="width:100%;">预览</a></div>'
      }
      var $btns = $(btnStr).appendTo($div);
      $btns.on('click', 'a', function() {
        if ($(this).hasClass('mu-remove')) {
          self.deleteThumb($div, callback2);
        }

        if ($(this).hasClass('mu-preview')) {
          self.preview($(this));
        }
      });

      if (self.setAsCover) {
        var $coverWrap = $('<p class="file-set-cover"><a href="javascript:void(0);">设置为封面</a></p>').appendTo($div);
        $coverWrap.find('a').click(function(e) {
          e.preventDefault();
          self.$element.nextAll().find('.file-set-cover').removeClass('is-cover');
          $(this).parent().addClass('is-cover');
          if (callback3) {
            callback3($div);
          }
        });
      }

      $div.appendTo(self.$container);

      if (callback) {
        callback($div);
      }
    },

    deleteThumb: function($el, callback) {
      var self = this;
      self.fileCount--;
      $el.remove();
      callback && callback($el)
    },

    emptyThumbs: function() {
      var self = this;
      self.$element.nextAll().each(function() {
        self.fileCount--;
        $(this).remove();
        self.uploader.reset();
      });
    },

    preview: function($el) {
      var self = this;
      var items = [];
      self.$container.find('.mu-thumbnail-item img').each(function() {
        var imgSrc = $(this).data('src') || $(this).attr('src');
        items.push({
          src: imgSrc,
          downloadUrl: imgSrc
        });
      });
      var thumbItems = self.$container.find('.mu-thumbnail-item');
      var index = thumbItems.index($el.closest('.mu-thumbnail-item'));
      if ($.fn.lightGallery) {
        $el.lightGallery({
          thumbnail: false,
          dynamic: true,
          index: index,
          dynamicEl: items
        })
      };
    }
  };

  $.fn.myuploader = function(options, callbacks) {
    this.each(function() {
      var $this = $(this);
      var myuploader = $this.data('myuploader');
      if (!myuploader) {
        myuploader = new MyUploader(this, options, callbacks);
        $this.data('myuploader', myuploader);
      }
    });
  };

  var defaults = $.fn.myuploader.defaults = {
    swf: 'js/vendor/webuploader.swf',
    chunked: true,
    thumbnailWidth: "80",
    thumbnailHeight: "80",
    auto: true,
    fileNumLimit: 5,
    compress: false,
    accept: {
      title: 'Images',
      extensions: 'gif,jpg,jpeg,bmp,png',
      mimeTypes: 'image/gif,image/jpg,image/jpeg,image/png'
    },
    container: ''
  };

  var defaultCallbacks = $.fn.myuploader.defaultCallbacks = {
    initUploaderCallback: function() {},
    addFileCallback: function() {},
    removeFileCallback: function() {},
    initUploaderImgCallback: function() {},
    uploadSuccessCallback: null,
    uploadErrorCallback: null,
    setAsCoverCallback: null
  };
  $.fn.myuploader.Constructor = MyUploader;
}));
