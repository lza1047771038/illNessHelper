package wust.student.illnesshepler.Bean;

import java.util.List;

public class GetTweetReply {

    private List<ReplyBean> reply;
    public static class ReplyBean {
        /**
         * id : 105
         * theme_id : null
         * time : 1573360578210
         * person_id : 13972008325
         * contains : hahahahaha
         * likes : 0
         * replies : 0
         * comments_num : 0
         * username : 老刘
         * userimage : null
         */

        private int id;
        private Object theme_id;
        private String time;
        private long person_id;
        private String contains;
        private int likes;
        private int replies;
        private int comments_num;
        private String username;
        private String userimage;
    }
}
