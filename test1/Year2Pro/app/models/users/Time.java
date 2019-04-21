package models.users;

import java.util.*;
import javax.persistence.*;
import io.ebean.*;
import play.data.format.*;
import play.data.validation.*;


public enum Time {
    ONE {
        @Override
        public String getTime() {
            return "8:00 - 10:00";
        }

        @Override
        public String getType() {
            return "8am - 10am";
        }

    }, TWO {
        @Override
        public String getTime() {
            return "10:00 - 12:00";
        }
        @Override
        public String getType() {
            return "10am - 12pm";
        }

    }, THREE {
        @Override
        public String getTime() {
            return "12:00 - 14:00";
        }
        @Override
        public String getType() {
            return "12pm - 2pm";
        }

    }, FOUR {
        @Override
        public String getTime() {
            return "14:00 - 16:00";
        }
        @Override
        public String getType() {
            return "2pm - 4pm";
        }
    }, FIVE {
        @Override
        public String getTime() {
            return "16:00 - 18:00";
        }
        @Override
        public String getType() {
            return "4pm - 6pm";
        }
    };

    public abstract String getTime();
    public abstract String getType();

    public static Map<String,String> options(){
        LinkedHashMap<String,String> options = new LinkedHashMap<>();
        for (Time t: Time.values()) {
            options.put(t.getTime(),t.getType());
        }
        return options;
    }
}
