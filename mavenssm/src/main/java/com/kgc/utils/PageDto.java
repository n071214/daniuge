package com.kgc.utils;


import java.util.List;

/**
 * @param <T>
 * @author CuiJunJia
 * @deprecated 分页使用的dto类
 */

public class PageDto<T> {

    //总条数
    private Long total;
    //当前页码，从一开始
    private Integer pageNo;
    //每页的最大数量，不是data集合的长度
    private Integer pageSize;
    //总页数
    private Integer pages;
    //当前页的数据集合
    private List<T> data;

    //第一页
//    private int firstPage;
//    //前一页
//    private int prePage;

//    //是否为第一页
//    private boolean isFirstPage = false;
//    //是否为最后一页
//    private boolean isLastPage = false;
    //是否有前一页
    private boolean hasPreviousPage = false;
    //是否有下一页
    private boolean hasNextPage = false;
//    //导航页码数
//    private int navigatePages;
//    //所有导航页号
//    private int[] navigatepageNums;






    /**
     * <mapper namespace="cn.cuijunjia.dao.realestate.RealestateMapper">
     * 	<resultMap type="Realestate" id="realMap">
     * 		<id column="id" property="id" />
     * 		<result property="cardname" column="name" />
     * 	</resultMap>
     * 	<select id="selectAllRealestateByNameAndCardId" parameterType="map" resultMap="realMap">
     * 		SELECT u.`name`,r.* FROM users u
     * 				INNER JOIN real_estate r
     * 				ON u.cardid = r.cardid
     * 			<where>
     * 				<if test="cardid!=null">
     * 					AND r.cardid LIKE CONCAT('%',#{cardid},'%')
     * 				</if>
     * 				<if test="name!=null">
     * 					AND u.name LIKE CONCAT('%',#{name},'%')
     * 				</if>
     * 			</where>
     * 			limit #{pageNo},#{pageSize}
     * 	</select>
     * 	<select id="getTotalCount" resultType="int" parameterType="map">
     * 		SELECT count(*) FROM users u
     * 				INNER JOIN real_estate r
     * 				ON u.cardid = r.cardid
     * 			<where>
     * 				<if test="cardid!=null">
     * 					AND r.cardid LIKE CONCAT('%',#{cardid},'%')
     * 				</if>
     * 				<if test="name!=null">
     * 					AND u.name LIKE CONCAT('%',#{name},'%')
     * 				</if>
     * 			</where>
     *
     * 	</select>
     *
     * </mapper>
     */


    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public boolean isHasPreviousPage() {
        return hasPreviousPage;
    }

    public void setHasPreviousPage(boolean hasPreviousPage) {
        this.hasPreviousPage = hasPreviousPage;
    }

    public boolean isHasNextPage() {
        return hasNextPage;
    }

    public void setHasNextPage(boolean hasNextPage) {
        this.hasNextPage = hasNextPage;
    }


    @Override
    public String toString() {
        return "PageDto{" +
                "total=" + total +
                ", pageNo=" + pageNo +
                ", pageSize=" + pageSize +
                ", pages=" + pages +
                ", data=" + data +
                ", hasPreviousPage=" + hasPreviousPage +
                ", hasNextPage=" + hasNextPage +
                '}';
    }
}
