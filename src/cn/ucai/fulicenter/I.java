package cn.ucai.fulicenter;

public interface I {

//    static interface User {
//        String TABLE_NAME = "t_superwechat_user";
//        String USER_NAME = "m_user_name";                    //用户账号
//        String PASSWORD = "m_user_password";                //用户密码
//        String NICK = "m_user_nick";                    //用户昵称
//    }

//    static interface Contact {
//        String TABLE_NAME = "t_superwechat_contact";
//        String CONTACT_ID = "m_contact_id";                    //主键
//        String USER_NAME = "m_contact_user_name";            //用户账号
//        String CU_NAME = "m_contact_cname";                //好友账号
//    }

//    	String AVATAR_PATH 								= 		"E:/test/";
    String ISON8859_1 = "iso8859-1";
    String UTF_8 = "utf-8";
    String PAGE_ID = "page_id";                        //分页的起始下标
    String PAGE_SIZE = "page_size";                    //分页的每页数量
    int DEFAULT_DISTANCE = 100; // 查找附近的人时，默认距离为100KM
    int ID_DEFAULT = 0;                                //ID默认值
    int UN_READ_MSG_COUNT_DEFAULT = 0;                                //未读消息数量默认值
    int GROUP_MAX_USERS_DEFAULT = -1;                                //群组最大人数默认值
    int GROUP_AFFILIATIONS_COUNT_DEFAULT = 1;                                //群组人数默认值
    int PERMISSION_NORMAL = 0;                                //普通用户群组权限
    int PERMISSION_OWNER = 1;                                //群组所有者群组权限
    int AVATAR_TYPE_USER = 0;                                //用户头像
    int AVATAR_TYPE_GROUP = 1;                                //群组头像
    int GROUP_PUBLIC = 1;                                //公开群组
    int GROUP_NO_PUBLIC = 0;                                //非公开群组
    String BACKSLASH = "/";                            //反斜杠
    String AVATAR_TYPE_USER_PATH = "user_avatar";                    //用户头像保存目录
    String AVATAR_TYPE_GROUP_PATH = "group_icon";                    //群组头像保存目录
    String AVATAR_SUFFIX_PNG = ".png";                            //PNG图片后缀名
    String AVATAR_SUFFIX_JPG = ".jpg";                            //JPG图片后缀名
    String QUESTION = "?";                            //问号
    String EQU = "=";
    String AND = "&";                                //
    String MSG_PREFIX_MSG = "msg_";                            //消息码前缀
    int LOCATION_IS_SEARCH_ALLOW = 1;                                //可以被搜索到地理位置
    int LOCATION_IS_SEARCH_INHIBIT = 0;                                //禁止被搜索到地理位置
    int MSG_SUCCESS = 0;                                //默认成功
    int MSG_REGISTER_USERNAME_EXISTS = 101;                            //账号已经存在
    int MSG_REGISTER_FAIL = 102;                            //注册失败
    int MSG_UNREGISTER_FAIL = 103;                            //解除注册失败
    int MSG_USER_SEARCH_FAIL = 104;                            // 查找用户失败
    int MSG_LOGIN_UNKNOW_USER = 105;                            //账户不存在
    int MSG_LOGIN_ERROR_PASSWORD = 106;                            //账户密码错误
    int MSG_LOGIN_SUCCESS = 107;                            //登陆成功
    int MSG_USER_SAME_NICK = 108;                            //昵称未修改
    int MSG_USER_UPDATE_NICK_FAIL = 109;                            //昵称修改失败
    int MSG_USER_SAME_PASSWORD = 110;                            //昵称未修改
    int MSG_USER_UPDATE_PASSWORD_FAIL = 111;                            //昵称修改失败
    int MSG_LOCATION_UPLOAD_FAIL = 112;                            //用户上传地理位置失败
    int MSG_LOCATION_UPDATE_FAIL = 113;                            //用户更新地理位置失败
    int MSG_REGISTER_UPLOAD_AVATAR_FAIL = 201;                            //上传头像失败
    int MSG_UPLOAD_AVATAR_FAIL = 202;                            //更新头像失败
    int MSG_CONTACT_FIRENDED = 301;                            //已经是好友关系
    int MSG_CONTACT_ADD_FAIL = 302;                            //好友关系添加失败
    int MSG_CONTACT_DEL_FAIL = 303;                            //好友关系删除失败
    int MSG_GET_CONTACT_ALL_FAIL = 304;                            // 获取全部好友列表失败
    int MSG_GET_CONTACT_PAGES_FAIL = 305;                            // 分页获取好友列表失败
    int MSG_GROUP_HXID_EXISTS = 401;                            //群组环信ID已经存在
    int MSG_GROUP_CREATE_FAIL = 402;                            //创建群组失败
    int MSG_GROUP_ADD_MEMBER_FAIL = 403;                            //添加群组成员失败
    int MSG_GROUP_GET_MEMBERS_FAIL = 404;                            //获取群成员失败
    int MSG_GROUP_UNKONW = 405;                            //群组不存在
    int MSG_GROUP_SAME_NAME = 406;                            //有相同群组名称
    int MSG_GROUP_UPDATE_NAME_FAIL = 407;                            //群组名称修改失败
    int MSG_GROUP_DELETE_MEMBER_FAIL = 408;                            //删除群组成员失败
    int MSG_GROUP_DELETE_MEMBERS_FAIL = 409;                            //删除多群组成员失败
    int MSG_GROUP_DELETE_FAIL = 410;                            //删除群组失败
    int MSG_GROUP_FIND_BY_GOURP_ID_FAIL = 411;                            //根据群组id查找群组失败
    int MSG_GROUP_FIND_BY_HX_ID_FAIL = 412;                            //根据环信id查找群组失败
    int MSG_GROUP_FIND_BY_USER_NAME_FAIL = 413;                            //查找用户名称查找查找群组失败
    int MSG_GROUP_FIND_BY_GROUP_NAME_FAIL = 414;                            //查找群组名称查找查找群组失败
    int MSG_PUBLIC_GROUP_FAIL = 415;                            //查找公开群失败
    int MSG_LOCATION_GET_FAIL = 501;                            // 获取附近的人失败
    int MSG_UNKNOW = 999;                            //未知错误
    int MSG_ILLEGAL_REQUEST = -1;                            //非法请求
    String SERVER_ROOT = "http://10.0.2.2:8888/FuLiCenterServer/Server";
    String KEY_REQUEST = "request";
    /**
     * 上传图片的类型：user_avatar或group_icon
     */
    String AVATAR_TYPE = "avatarType";
    /**
     * 用户姓名或hxid
     */
    String NAME_OR_HXID = "name_or_hxid";
    /**
     * 服务器状态的请求
     */
    String REQUEST_SERVERSTATUS = "server_status";
    /**
     * 客户端发送的注册请求
     */
    String REQUEST_REGISTER = "register";
    /**
     * 发送取消注册的请求
     */
    String REQUEST_UNREGISTER = "unregister";
    /**
     * 客户端上传头像的请求
     */
    String REQUEST_UPLOAD_AVATAR = "upload_avatar";
    /**
     * 客户端更新用户昵称的请求
     */
    String REQUEST_UPDATE_USER_NICK = "update_nick";
    /**
     * 客户端修改密码的请求
     */
    String REQUEST_UPDATE_USER_PASSWORD = "update_password";
    /**
     * 客户端发送的登陆请求
     */
    String REQUEST_LOGIN = "login";
    /**
     * 客户端发送的下载用户头像请求
     */
    String REQUEST_DOWNLOAD_AVATAR = "download_avatar";
    /**
     * 客户端发送的下载联系人所有集合请求
     */
    String REQUEST_DOWNLOAD_CONTACT_ALL_LIST = "download_contact_all_list";
    /**
     * 客户端发送的下载联系人集合请求
     */
    String REQUEST_DOWNLOAD_CONTACT_PAGE_LIST = "download_contact_page_list";
    /**
     * 客户端发送的删除联系人请求
     */
    String REQUEST_DELETE_CONTACT = "delete_contact";
    /**
     * 客户端发送的添加联系人请求
     */
    String REQUEST_ADD_CONTACT = "add_contact";
    /**
     * 客户端发送的查找用户请求
     */
    String REQUEST_FIND_USER = "find_user";
    /**
     * 客户端发送的根据用户或昵称模糊查找用户请求
     */
    String REQUEST_FIND_USERS_FOR_SEARCH = "find_users_for_search";
    /**
     * 客户端发送的上传位置请求
     */
    String REQUEST_UPLOAD_LOCATION = "upload_location";
    /**
     * 客户端发送的更新位置请求
     */
    String REQUEST_UPDATE_LOCATION = "update_location";
    /**
     * 客户端发送的下载位置请求
     */
    String REQUEST_DOWNLOAD_LOCATION = "download_location";
    /**
     * 客户端发送的创建群组请求
     */
    String REQUEST_CREATE_GROUP = "create_group";
    /**
     * 客户端发送的添加群成员请求
     */
    String REQUEST_ADD_GROUP_MEMBER = "add_group_member";
    /**
     * 客户端发送的添加多个群成员请求
     */
    String REQUEST_ADD_GROUP_MEMBERS = "add_group_members";
    /**
     * 客户端发送的更新群名称请求
     */
    String REQUEST_UPDATE_GROUP_NAME = "update_group_name";
    /**
     * 客户端发送的下载多个群成员请求
     */
    String REQUEST_DOWNLOAD_GROUP_MEMBERS = "download_group_members_by_groupid";
    /**
     * 客户端发送的下载多个群成员请求
     */
    String REQUEST_DOWNLOAD_GROUP_MEMBERS_BY_LIMIT = "download_group_members_by_limit";
    /**
     * 客户端发送的下载多个群成员请求
     */
    String REQUEST_DOWNLOAD_GROUP_MEMBERS_BY_HXID = "download_group_members_by_hxid";
    /**
     * 客户端发送的下载多个群成员请求
     */
    String REQUEST_DOWNLOAD_GROUP_MEMBERS_BY_HXID_LIMIT = "download_group_members_by_hxid_limit";
    /**
     * 客户端发送的删除群成员请求
     */
    String REQUEST_DELETE_GROUP_MEMBER = "delete_group_member";
    /**
     * 客户端发送的删除多个群成员请求
     */
    String REQUEST_DELETE_GROUP_MEMBERS = "delete_group_members";
    /**
     * 客户端发送的删除群组请求
     */
    String REQUEST_DELETE_GROUP = "delete_group";
    /**
     * 客户端发送的下载公开裙请求
     */
    String REQUEST_FIND_PUBLIC_GROUPS = "download_public_groups";
    /**
     * 客户端发送的根据群组名称模糊查找群组请求
     */
    String REQUEST_FIND_GROUP_BY_GROUP_NAME = "find_group_by_group_name";
    /**
     * 客户端发送的用户姓名查找用户所在的群组请求
     */
    String REQUEST_FIND_GROUP_BY_USER_NAME = "find_group_by_user_name";
    /**
     * 客户端发送的根据群组账号查找群组请求
     */
    String REQUEST_FIND_GROUP_BY_ID = "find_group_by_group_id";
    /**
     * 客户端发送的根据群组环信id查找群组请求
     */
    String REQUEST_FIND_GROUP_BY_HXID = "find_group_by_group_hxid";
    /**
     * 客户端发送的根据群组环信id查找公开群组请求
     */
    String REQUEST_FIND_PUBLIC_GROUP_BY_HXID = "find_public_group_by_group_hxid";
    /**
     * 上传图片的类型：user_avatar或group_icon
     */
//    public static final String AVATAR_TYPE = "avatarType";
    public static final String FILE_NAME = "file_name";
    public static final int PAGE_ID_DEFAULT = 0;
    public static final int PAGE_SIZE_DEFAULT = 10;
    public static final int REQUEST_CODE_LOGIN = 1;


    //    String SERVER_ROOT="http://10.0.2.2:8080/FuLiCenterServer/Server";
//    String SERVER_ROOT = "http://139.196.185.33:8080/FuLiCenterServer/Server";
    //String SERVER_ROOT="http://139.196.185.33:8080/SuperQQ3Server/Server";

    /**
     * 页号
     */
//    public static final String PAGE_ID = "page_id";

    /**
     * 每页加载的数据
     */
//    public static final String PAGE_SIZE = "page_size";
    public static final int ACTIVITY_REGISTER_REQUEST_CODE = 2;
    /**
     * 下拉刷新
     */
    public static final int ACTION_DOWNLOAD = 0;
    /**
     * 第一次下载
     */
    public static final int ACTION_PULL_DOWN = 1;
    /**
     * 上拉刷新
     */
    public static final int ACTION_PULL_UP = 2;
    /**
     * 每行显示的数量columNum
     */
    public static final int COLUM_NUM = 2;
    /**
     * 表示列表项布局的两种类型
     */
    public static final int TYPE_ITEM = 0;
    public static final int TYPE_FOOTER = 1;
    /**
     * BeeColud APP ID
     */
    public static final String BEE_COLUD_APP_ID = "3539b590-4859-4128-87a3-5fb8b86b94f6";
    /**
     * BeeColud APP Secret
     */
    public static final String BEE_COLUD_APP_SECRET = "c75c74e1-105e-437c-9be9-84c4ddee4d5f";
    /**
     * BeeColud APP Test Secret
     */
    public static final String BEE_COLUD_APP_SECRET_TEST = "06eb1210-0eeb-41df-99e3-1ffb9eb87b99";
    /**
     * weixin APP ID
     */
    public static final String WEIXIN_APP_ID = "wxf1aa465362b4c8f1";
    // 如果使用PayPal需要在支付之前设置client id和应用secret
    public static final String PAYPAL_CLIENT_ID = "AVT1Ch18aTIlUJIeeCxvC7ZKQYHczGwiWm8jOwhrREc4a5FnbdwlqEB4evlHPXXUA67RAAZqZM0H8TCR";
    public static final String PAYPAL_SECRET = "EL-fkjkEUyxrwZAmrfn46awFXlX-h2nRkyCVhhpeVdlSRuhPJKXx3ZvUTTJqPQuAeomXA8PZ2MkX24vF";
    //商户名称
    public static final String MERCHANT_NAME = "福利社";
    //货币单位
    public static final String CURRENCY_TYPE_CNY = "CNY";
    public static final String CURRENCY_TYPE_USD = "USD";
    public final int NEW_GOOD = 0;
    public final int CATEGORY_GOOD = 1;
    public final int CAT_ID = 0;
    /**
     * 商品排序方式
     */
    public final int SORT_BY_PRICE_ASC = 1;
    public final int SORT_BY_PRICE_DESC = 2;
    public final int SORT_BY_ADDTIME_ASC = 3;
    public final int SORT_BY_ADDTIME_DESC = 4;
    /**
     * 下载头像
     */
//    String REQUEST_DOWNLOAD_AVATAR = "download_avatar";

    String REQUEST_DOWNLOAD_CONTACTS = "download_contacts";
    String REQUEST_DOWNLOAD_CONTACT_LIST = "download_contactlist";
    /**
     * 查找联系人
     */
//    String REQUEST_FIND_USER = "find_user";
    String REQUEST_FIND_CHARGE = "find_charge";
    /**
     * 从服务端查询精选首页的数据
     */
    String REQUEST_FIND_BOUTIQUES = "find_boutiques";
    /**
     * 从服务端查询新品或精选的商品
     */
    String REQUEST_FIND_NEW_BOUTIQUE_GOODS = "find_new_boutique_goods";
    /**
     * 从服务端下载tb_category_parent表的数据
     */
    String REQUEST_FIND_CATEGORY_GROUP = "find_category_group";
    /**
     * 从服务端下载tb_category_child表的数据
     */
    String REQUEST_FIND_CATEGORY_CHILDREN = "find_category_children";
    /**
     * 从服务端下载tb_category_good表的数据
     */
    String REQUEST_FIND_GOOD_DETAILS = "find_good_details";
    /**
     * 从服务端下载一组商品详情的数据
     */
    String REQUEST_FIND_GOODS_DETAILS = "find_goods_details";
    /**
     * 下载指定小类别的颜色列表
     */
    String REQUEST_FIND_COLOR_LIST = "find_color_list";
    /**
     * 查询是否已收藏
     */
    String REQUEST_IS_COLLECT = "is_collect";
    /**
     * 添加收藏
     */
    String REQUEST_ADD_COLLECT = "add_collect";
    /**
     * 删除收藏
     */
    String REQUEST_DELETE_COLLECT = "delete_collect";
    /**
     * 下载收藏的商品信息
     */
    String REQUEST_FIND_COLLECTS = "find_collects";
    /**
     * 下载收藏的商品数量信息
     */
    String REQUEST_FIND_COLLECT_COUNT = "find_collect_count";
    String REQUEST_ADD_CART = "add_cart";
    String REQUEST_FIND_CARTS = "find_carts";


//    String KEY_REQUEST = "request";
    /**
     * 客户端发送的获得服务器状态的请求
     */
//    String REQUEST_SERVERSTATUS = "server_status";
    /**
     * 客户端发送的注册请求
     */
//    String REQUEST_REGISTER = "register";
    /**
     * 发送取消注册的请求
     */
//    String REQUEST_UNREGISTER = "unregister";
    /**
     * 上传头像
     */
//    String REQUEST_UPLOAD_AVATAR = "upload_avatar";
    /**
     * 登陆
     */
//    String REQUEST_LOGIN = "login";
    String REQUEST_DELETE_CART = "delete_cart";
    String REQUEST_UPDATE_CART = "update_cart";

//    String REQUEST_DELETE_CONTACT = "delete_contact";
    /**
     * 添加联系人
     */
//    String REQUEST_ADD_CONTACT = "add_contact";
    /**
     * 下载新品首页商品图片
     */
    String REQUEST_DOWNLOAD_NEW_GOOD = "download_new_good";
    /**
     * 下载商品属性颜色的图片
     */
    String REQUEST_DOWNLOAD_COLOR_IMG = "download_color_img";
    /**
     * 下载商品相册图像的URL
     */
    String DOWNLOAD_AVATAR_URL = SERVER_ROOT +
            "?request=" + REQUEST_DOWNLOAD_AVATAR + "&avatar=";
    /**
     * 下载商品相册图像的请求
     */
    String REQUEST_DOWNLOAD_ALBUM_IMG = "download_album_img_url";
    /**
     * 下载商品相册图像的接口
     */
    String DOWNLOAD_ALBUM_IMG_URL = SERVER_ROOT +
            "?request=" + REQUEST_DOWNLOAD_ALBUM_IMG + "&img_url=";
    /**
     * 下载精选首页图像的请求
     */
    String REQUEST_DOWNLOAD_BOUTIQUE_IMG = "download_boutique_img";
    /**
     * 下载精选首页图像的接口
     */
    String DOWNLOAD_BOUTIQUE_IMG_URL = SERVER_ROOT +
            "?request=" + REQUEST_DOWNLOAD_BOUTIQUE_IMG + "&" + Boutique.IMAGE_URL + "=";
    /**
     * 下载分类商品大类图像的请求
     */
    String REQUEST_DOWNLOAD_CATEGORY_GROUP_IMAGE = "download_category_group_image";
    /**
     * 下载分类商品大类图像的接口
     */
    String DOWNLOAD_DOWNLOAD_CATEGORY_GROUP_IMAGE_URL = SERVER_ROOT +
            "?request=" + REQUEST_DOWNLOAD_CATEGORY_GROUP_IMAGE
            + "&" + D.CategoryGroup.IMAGE_URL + "=";
    /**
     * 下载收藏商品图像的请求
     */
    String REQUEST_DOWNLOAD_GOODS_THUMB = "download_goods_thumb";
    /**
     * 下载收藏商品图像的接口
     */
    String DOWNLOAD_GOODS_THUMB_URL = SERVER_ROOT +
            "?request=" + REQUEST_DOWNLOAD_GOODS_THUMB
            + "&" + Collect.GOODS_THUMB + "=";
    /**
     * 下载分类商品小类图像的请求
     */
    String REQUEST_DOWNLOAD_CATEGORY_CHILD_IMAGE = "download_category_child_image";
    /**
     * 下载分类商品小类图像的接口
     */
    String DOWNLOAD_DOWNLOAD_CATEGORY_CHILD_IMAGE_URL = SERVER_ROOT +
            "?request=" + REQUEST_DOWNLOAD_CATEGORY_GROUP_IMAGE
            + "&" + D.CategoryChild.IMAGE_URL + "=";
    String REQUEST_UPLOAD_NICK = "upload_nick";
    //壹收款支付请求
    String REQUEST_PAY = "pay";
    /**
     * 壹收款服务端支付URL
     */
    String PAY_URL = I.SERVER_ROOT + "?request=" + I.REQUEST_PAY;

    public enum ActionType {
        ACTION_DOWNLOAD, ACTION_PULL_DOWN, ACTION_SCROLL
    }

    static interface Group {
        String TABLE_NAME = "t_superwechat_group";
        String GROUP_ID = "m_group_id";                    // 主键
        String HX_ID = "m_group_hxid";                    //环信群组id
        String NAME = "m_group_name";                    //群组名称
        String DESCRIPTION = "m_group_description";            //群组简介
        String OWNER = "m_group_owner";                //群组所有者－用户账号
        String MODIFIED_TIME = "m_group_last_modified_time";    //最后修改时间
        String MAX_USERS = "m_group_max_users";            //最大人数
        String AFFILIATIONS_COUNT = "m_group_affiliations_count";    //群组人数
        String IS_PUBLIC = "m_group_is_public";            //群组是否公开
        String ALLOW_INVITES = "m_group_allow_invites";        //是否可以邀请
    }

    static interface Member {
        String TABLE_NAME = "t_superwechat_member";
        String MEMBER_ID = "m_member_id";                    //主键
        String USER_NAME = "m_member_user_name";            //用户账号
        String GROUP_ID = "m_member_group_id";            //群组id
        String GROUP_HX_ID = "m_member_group_hxid";            //群组环信id
        String PERMISSION = "m_member_permission";            //用户对群组的权限\n0:普通用户\n1:群组所有者
    }

    static interface Avatar {
        String TABLE_NAME = "t_superwechat_avatar";
        String AVATAR_ID = "m_avatar_id";                    //主键
        String USER_NAME = "m_avatar_user_name";            //用户账号或者群组账号
        String AVATAR_PATH = "m_avatar_path";                //保存路径
        String AVATAR_TYPE = "m_avatar_type";                //头像类型：\n0:用户头像\n1:群组头像
        String UPDATE_TIME = "m_avatar_last_update_time";    //最后更新时间
    }

    static interface Location {
        String TABLE_NAME = "t_superwechat_location";
        String LOCATION_ID = "m_location_id";                //主键
        String USER_NAME = "m_location_user_name";            //用户账号
        String LATITUDE = "m_location_latitude";            //纬度
        String LONGITUDE = "m_location_longitude";            //经度
        String IS_SEARCHED = "m_location_is_searched";        //是否可以被搜索到
        String UPDATE_TIME = "m_location_last_update_time";    //最后更新时间
    }

    class Cart {
        public static final String ID = "id";
        public static final String GOODS_ID = "goods_id";
        public static final String GOODS_THUMB = "goodsThumb";
        public static final String USER_NAME = "userName";
        /**
         * 购物车中该商品的件数
         */
        public static final String COUNT = "count";
        /**
         * 商品是否已被选中
         */
        public static final String IS_CHECKED = "isChecked";
    }

    class Collect {
        /**
         * 商品id
         */
        public static final String ID = "id";

        public static final String GOODS_ID = "goods_id";

        public static final String USER_NAME = "userName";

        /**
         * 商品的中文名称
         */
        public static final String GOODS_NAME = "goodsName";
        /**
         * 商品的英文名称
         */
        public static final String GOODS_ENGLISH_NAME = "goodsEnglishName";
        public static final String GOODS_THUMB = "goodsThumb";
        public static final String GOODS_IMG = "goodsImg";
        public static final String ADD_TIME = "addTime";
    }

    class Boutique {
        public static final String TABLE_NAME = "tb_boutique";
        public static final String ID = "id";
        public static final String CAT_ID = "catId";
        public static final String TITLE = "title";
        public static final String DESCRIPTION = "description";
        public static final String NAME = "name";
        public static final String IMAGE_URL = "imageurl";
    }

    class NewAndBoutiqueGood {
        public static final String CAT_ID = "cat_id";
        /**
         * 颜色id
         */
        public static final String COLOR_ID = "color_id";
        /**
         * 颜色名
         */
        public static final String COLOR_NAME = "color_name";
        /**
         * 颜色代码
         */
        public static final String COLOR_CODE = "color_code";
        /**
         * 导购链接
         */
        public static final String COLOR_URL = "color_url";
    }

    class CategoryGroup {
        public static final String ID = "id";
        public static final String NAME = "name";
        public static final String IMAGE_URL = "imageurl";
    }

    class CategoryChild extends CategoryGroup {
        public static final String PARENT_ID = "parent_id";
        public static final String CAT_ID = "catId";
    }

    class CategoryGood {
        public static final String TABLE_NAME = "tb_category_good";
        public static final String ID = "id";
        /**
         * 商品id
         */
        public static final String GOODS_ID = "goods_id";
        /**
         * 所属类别的id
         */
        public static final String CAT_ID = "cat_id";
        /**
         * 商品的中文名称
         */
        public static final String GOODS_NAME = "goods_name";
        /**
         * 商品的英文名称
         */
        public static final String GOODS_ENGLISH_NAME = "goods_english_name";
        /**
         * 商品简介
         */
        public static final String GOODS_BRIEF = "goods_brief";
        /**
         * 商品原始价格
         */
        public static final String SHOP_PRICE = "shop_price";
        /**
         * 商品的RMB价格
         */
        public static final String CURRENT_PRICE = "currency_price";
        /**
         * 商品折扣价格
         */
        public static final String PROMOTE_PRICE = "promote_price";
        /**
         * 人民币折扣价格
         */
        public static final String RANK_PRICE = "rank_price";
        /**
         * 是否折扣
         */
        public static final String IS_PROMOTE = "is_promote";
        /**
         * 商品缩略图地址
         */
        public static final String GOODS_THUMB = "goods_thumb";
        /**
         * 商品图片地址
         */
        public static final String GOODS_IMG = "goods_img";
        /**
         * 分享地址
         */
        public static final String ADD_TIME = "add_time";
        /**
         * 分享地址
         */
        public static final String SHARE_URL = "share_url";
    }

    class Property {
        public static final String ID = "id";
        public static final String goodsId = "goods_id";
        public static final String COLOR_ID = "colorid";
        public static final String COLOR_NAME = "colorname";
        public static final String COLOR_CODE = "colorcode";
        public static final String COLOR_IMG = "colorimg";
        public static final String COLOR_URL = "colorurl";
    }

    class Album {
        public static final String TABLE_NAME = "tb_album";
        public static final String ID = "id";
        public static final String PID = "pid";
        public static final String IMG_ID = "img_id";
        public static final String IMG_URL = "img_url";
        public static final String THUMB_URL = "thumb_url";
        public static final String IMG_DESC = "img_desc";
    }

    class Color {
        public static final String TABLE_NAME = "tb_color";
        public static final String COLOR_ID = "colorid";
        public static final String CAT_ID = "cat_id";
        public static final String COLOR_NAME = "colorname";
        public static final String COLOR_CODE = "colorcode";
        public static final String COLOR_IMG = "colorimg";
    }

    /**
     * User
     */
    public static class User {
        public static final String ID = "id";
        public static final String UID = "uid";
        public static final String USER_NAME = "userName";

        public static final String NICK = "nick";
        public static final String AVATAR = "avatar";
        public static final String HEADER = "header";
        public static final String PASSWORD = "password";
        public static final String UN_READ_MSG_COUNT = "unreadMsgCount";
        public static final String GROUPS = "groups";

    }

    public static class Contact extends User {
        public static final String TABLE_NAME = "tb_contact";
        public static final String NAME = "name";
        public static final String MYUID = "myuid";
        public static final String CUID = "cuid";
        public static final String IS_GET_MY_LOCATION = "isGetMyLocation";
        public static final String IS_SHOW_MY_LOCATION = "isShowMyLocation";
    }

}
