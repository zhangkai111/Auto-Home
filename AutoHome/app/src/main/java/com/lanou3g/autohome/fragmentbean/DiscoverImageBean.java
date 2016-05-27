package com.lanou3g.autohome.fragmentbean;

import java.util.List;

/**
 * Created by dllo on 16/5/13.
 */
public class DiscoverImageBean {


    /**
     * returncode : 0
     * message :
     * result : {"list":[{"id":6135,"title":"20160512","shorttitle":"标致4008","url":"http://m.mall.autohome.com.cn/detail/62086-110100-0.html#pvareaid=104735","imgurl":"http://app2.autoimg.cn/appdfs/g15/M03/7B/8C/autohomecar__wKjByFc0JEuAIzIqAAIgjPFdqpo790.jpg","urlscheme":"","type":2,"appicon":"","siteindex":0},{"id":6139,"title":"20160512","shorttitle":"北汽幻速S2","url":"http://m.mall.autohome.com.cn/detail/101469-0-0.html#pvareaid=104735","imgurl":"http://app2.autoimg.cn/appdfs/g13/M00/7B/2F/autohomecar__wKgH41c0JXmAHp8mAAJPf5o5qnU821.jpg","urlscheme":"","type":2,"appicon":"","siteindex":0},{"id":6142,"title":"513","shorttitle":"零首付分期","url":"http://athm.cn/fehnE","imgurl":"http://app2.autoimg.cn/appdfs/g6/M0C/7A/A6/autohomecar__wKgH3Fc0TCWAbFoKAADmBAPz7rw594.jpg","urlscheme":"","type":2,"appicon":"","siteindex":0},{"id":6133,"title":"20160512","shorttitle":"奇瑞eQ B2C","url":"http://m.mall.autohome.com.cn/detail/46818-110100-0.html#pvareaid=104735","imgurl":"http://app2.autoimg.cn/appdfs/g13/M01/7A/79/autohomecar__wKgH1Fc0H9uAFSXqAAIofv_C3PM383.jpg","urlscheme":"","type":2,"appicon":"","siteindex":0},{"id":6138,"title":"20160512","shorttitle":"大迈X5 B2C","url":"http://m.mall.autohome.com.cn/detail/88137-0-0.html#pvareaid=104735","imgurl":"http://app2.autoimg.cn/appdfs/g23/M11/5C/B1/autohomecar__wKgFV1c0JN-AG32tAAJDtcJJ8WU684.jpg","urlscheme":"","type":2,"appicon":"","siteindex":0}]}
     */

    private int returncode;
    private String message;
    private ResultBean result;

    public int getReturncode() {
        return returncode;
    }

    public void setReturncode(int returncode) {
        this.returncode = returncode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * id : 6135
         * title : 20160512
         * shorttitle : 标致4008
         * url : http://m.mall.autohome.com.cn/detail/62086-110100-0.html#pvareaid=104735
         * imgurl : http://app2.autoimg.cn/appdfs/g15/M03/7B/8C/autohomecar__wKjByFc0JEuAIzIqAAIgjPFdqpo790.jpg
         * urlscheme :
         * type : 2
         * appicon :
         * siteindex : 0
         */

        private List<ListBean> list;

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            private int id;
            private String title;
            private String shorttitle;
            private String url;
            private String imgurl;
            private String urlscheme;
            private int type;
            private String appicon;
            private int siteindex;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getShorttitle() {
                return shorttitle;
            }

            public void setShorttitle(String shorttitle) {
                this.shorttitle = shorttitle;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getImgurl() {
                return imgurl;
            }

            public void setImgurl(String imgurl) {
                this.imgurl = imgurl;
            }

            public String getUrlscheme() {
                return urlscheme;
            }

            public void setUrlscheme(String urlscheme) {
                this.urlscheme = urlscheme;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public String getAppicon() {
                return appicon;
            }

            public void setAppicon(String appicon) {
                this.appicon = appicon;
            }

            public int getSiteindex() {
                return siteindex;
            }

            public void setSiteindex(int siteindex) {
                this.siteindex = siteindex;
            }
        }
    }
}
