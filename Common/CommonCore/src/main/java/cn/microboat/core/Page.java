package cn.microboat.core;

/**
 * 分页
 *
 * @author zhouwei
 */
public class Page {

    /**
     * 当前页数
     */
    private Integer page;

    /**
     * 一页的长度
     */
    private Integer size;

    public Page() {
    }

    public Page(Integer page, Integer size) {
        this.page = page;
        this.size = size;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }
}
