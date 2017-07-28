package location.in.unitedbyhcl;

import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.support.annotation.RequiresApi;


/**
 * Created by shubb on 7/20/2017.
 */

@RequiresApi(api = Build.VERSION_CODES.N)
public class events {

    String name;
    String date;
    String time ;
    String info;
    String venue;
    String img;
    String url;
    Integer fees;
    String Spons;
    Integer priority;

    public events(String name, String info, String venue, String date, String time, String img, String url, String Spons, int fees, int priority) {
        this.name = name;
        this.info=info;
        this.venue=venue;
        this.date=date;
        this.time=time;
        this.img=img;
        this.url=url;
        this.Spons=Spons;
        this.fees=fees;
        this.priority=priority;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getinfo() {  return info;   }

    public void setinfo(String info) {  this.info = info;   }

    public String getvenue() { return venue;    }

    public void setvenue(String venue) { this.venue = venue;    }

    public String  getDate() {    return date;   }

    public void setDate(String date) {    this.date = date;    }

    public String getTime() {       return time;   }

    public void setTime(String time) {   this.time = time;    }

    public String getImg() {     return img;    }

    public void setImg(String img) {   this.img = img;   }

    public String getUrl() {        return url;    }

    public void setUrl(String url) {     this.url = url;        }

    public Integer getFees() {      return fees;    }

    public void setFees(Integer fees) {        this.fees = fees;    }

    public String getSpons() {      return Spons;    }

    public void setSpons(String spons) {    this.Spons = spons;   }

    public Integer getPriority() {       return priority;    }

    public void setPriority(Integer priority) {        this.priority = priority;    }

}
