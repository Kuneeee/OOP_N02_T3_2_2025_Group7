package quanLyBanHang.review;

public class Time {
    int hour;
    int minute;
    int second;

    
    Time() { setTime(0,0,0);}
    Time(int h){setTime(h,0,0);}
    Time(int h,int m){setTime(h,m,0);}
    Time(int h, int m, int s) {setTime(h,m,s);}
}
