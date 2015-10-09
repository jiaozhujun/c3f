package com.youcan.core.util;

public class Pager {
    /*
     *@每页显示数据数
     */
    private int limit = 20;

    /*
     *@每页的开始记录行
     */
    private int start = 0;

    /*
     *@当前页
     */
    private int cp = 1;

    /*
     *@总页数
     */
    private int tp = 1;

    /*
     *@总记录数
     */
    private int totalNum =0;

    /*
     * @步长
     */
    private int step = 6;

    /*
     * @上一页
     */
    private int prevPage = 0;
    
    /*
     * @下一页
     */
    private int nextPage = 0;

    /*
     *@构造函数
     */
    public Pager(int totalNum, int cp, int limit, int step) {
    	this.totalNum = totalNum;
    	this.cp = cp;
    	this.limit = limit;
    	this.step = step;
    	this.tp = divCeil(this.totalNum, this.limit);
        if (this.cp <= 0) {
        	this.cp = 1;
        } else if (this.cp > this.tp) {
        	this.cp = this.tp;
        }
        this.start = (this.cp - 1) * this.limit;
    }
    
    public Pager() {
    	this(0, 1, 20, 6);
    }
    
    @SuppressWarnings("static-method")
	private int divCeil(int a, int b) {
    	return a/b + (a%b > 0 ? 1 : 0);
    }

    public int[] getStep(){ 	
        int fpagenum = this.cp + divCeil(this.step, 2);
        int spagenum = this.cp - divCeil(this.step, 2);
        int tmp = 0, step2;

        if (spagenum < 0) { 		
        	spagenum = 1;
            tmp = spagenum + this.step;
            fpagenum = tmp > this.tp ? this.tp : tmp;
        } else { 		
            if((spagenum >= 0) && (fpagenum >= this.tp)){
                fpagenum = this.tp; 			
            }
            step2 = fpagenum - this.step;
            spagenum = step2 > 0 ? step2 : 1;
        }

        int[] re = new int[fpagenum - spagenum + 1];
        int j = 0;
        for (int i = spagenum; i <= fpagenum; i++){
            re[j++]=i;
        }
        return re;
    }

    public String build(String url, String varpage){
        StringBuffer pageBuf = new StringBuffer();
        prevPage = cp - 1;
        nextPage = cp + 1;

        //首页、上一页
        if (cp == 1) {
        	pageBuf.append("<span>").append("首页").append("</span>");
        	pageBuf.append("<span>").append("上一页").append("</span>");
        } else {
        	pageBuf.append("<a href=\"").append(url).append('&').append(varpage).append('=').append(1).append("\">首页</a>");
        	pageBuf.append("<a href=\"").append(url).append('&').append(varpage).append('=').append(prevPage).append("\">上一页</a>");
        }
        
        //step
        int[] steppage = getStep();
        for (int value : steppage){
            if(value == cp) {
            	pageBuf.append("<span>").append(value).append("</span>");
            }else {
                pageBuf.append("<a href=\"").append(url).append('&').append(varpage).append('=').append(value).append("\">").append(value).append("</a>");
            }
        }
        
        //下一页、末页
        if (cp == tp) {
        	pageBuf.append("<span>").append("下一页").append("</span>");
        	pageBuf.append("<span>").append("末页").append("</span>");
        } else {
        	pageBuf.append("<a href=\"").append(url).append('&').append(varpage).append('=').append(nextPage).append("\">下一页</a>");
        	pageBuf.append("<a href=\"").append(url).append('&').append(varpage).append('=').append(tp).append("\">末页</a>");
        }

        return pageBuf.toString();
    }

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getCp() {
		return cp;
	}

	public void setCp(int cp) {
    	this.cp = cp;
    	tp = divCeil(this.totalNum, this.limit);
        if (this.cp <= 0) {
        	this.cp = 1;
        } else if (this.cp > tp) {
        	this.cp = tp;
        }
        start = (cp - 1) * limit;
	}

	public int getTp() {
		return tp;
	}

	public void setTp(int tp) {
		this.tp = tp;
	}

	public int getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(int totalNum) {
		this.totalNum = totalNum;
	}
}
