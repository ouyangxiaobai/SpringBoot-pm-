package com.yuanlrc.base.dao.admin;

import com.yuanlrc.base.entity.common.LabelType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 标类型数据库处理层
 * @author Administrator
 *
 */
@Repository
public interface LabelTypeDao extends JpaRepository<LabelType, Long>,JpaSpecificationExecutor<LabelType> {


	/**
	 * 根据标类型id查询
	 * @param id
	 * @return
	 */
	@Query("select lt from LabelType lt where id = :id")
	public LabelType find(@Param("id") Long id);

	/**
	 * 根据标类型名称查询
	 * @param name
	 * @return
	 */
	public LabelType findByName(@Param("name")String name);

	/**
	 * 根据状态查询所有
	 * @param status
	 * @return
	 */
	public List<LabelType> findByStatus(int status);
}
