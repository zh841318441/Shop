package shop.cn.edu.com.shop;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TabHost;


import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.Poi;

import java.util.List;

import shop.cn.edu.com.activity.OrderActivity;
import shop.cn.edu.com.activity.PersioinAcitivity;
import shop.cn.edu.com.activity.ShopActivity;

public class MainActivity  extends TabActivity {
    private TabHost mTabHost;
    private RadioGroup mTabButtonGroup;
    public static final String TAB_MAIN = "SHOPLIST_ACTIVITY";
    public static final String TAB_BOOK = "ORDER_ACTIVITY";
    public static final String TAB_CATEGORY = "PERSON_ACTIVITY";
    public static final String ACTION_TAB = "tabaction";
    private RadioButton rButton1, rButton2, rButton3;
    public LocationClient mLocationClient = null;
    public BDLocationListener myListener = new MyLocationListener();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById();
        initView();
       System.out.println("abbbbbbbbbbbbbb");
        mLocationClient = new LocationClient(getApplicationContext());     //声明LocationClient类
        mLocationClient.registerLocationListener(myListener);
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy
        );//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
        int span=1000;
        option.setScanSpan(span);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setLocationNotify(true);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤gps仿真结果，默认需要
        mLocationClient.setLocOption(option);
        mLocationClient.start();
    }

    private void findViewById() {
        mTabButtonGroup = (RadioGroup) findViewById(R.id.home_radio_button_group);
        rButton1 = (RadioButton) findViewById(R.id.home_tab_main);
        rButton2 = (RadioButton) findViewById(R.id.home_tab_search);
        rButton3 = (RadioButton) findViewById(R.id.home_tab_category);
    }

    private void initView() {

        mTabHost = getTabHost();

        Intent i_main = new Intent(this, ShopActivity.class);
        Intent i_search = new Intent(this, OrderActivity.class);
        Intent i_category = new Intent(this, PersioinAcitivity.class);

        mTabHost.addTab(mTabHost.newTabSpec(TAB_MAIN).setIndicator(TAB_MAIN)
                .setContent(i_main));
        mTabHost.addTab(mTabHost.newTabSpec(TAB_BOOK).setIndicator(TAB_BOOK)
                .setContent(i_search));
        mTabHost.addTab(mTabHost.newTabSpec(TAB_CATEGORY)
                .setIndicator(TAB_CATEGORY).setContent(i_category));

        mTabButtonGroup
                .setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        switch (checkedId) {
                            case R.id.home_tab_main:
                                mTabHost.setCurrentTabByTag(TAB_MAIN);
                                changeTextColor(1);
                                break;
                            case R.id.home_tab_search:
                                mTabHost.setCurrentTabByTag(TAB_BOOK);
                                changeTextColor(2);

                                break;
                            case R.id.home_tab_category:
                                mTabHost.setCurrentTabByTag(TAB_CATEGORY);
                                changeTextColor(3);

                                break;
                            default:
                                break;
                        }
                    }
                });
    }

    private void changeTextColor(int index) {
        switch (index) {
            case 1:
                rButton1.setTextColor(getResources().getColor(
                        R.color.textcolor_select));

                break;
            case 2:
                rButton1.setTextColor(getResources().getColor(
                        R.color.textcolor_normal));

                break;
            case 3:
                rButton1.setTextColor(getResources().getColor(
                        R.color.textcolor_normal));

                break;

            default:
                break;
        }
    }

    public class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            //Receive Location
          System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
            StringBuffer sb = new StringBuffer(256);
            sb.append("time : ");
            sb.append(location.getTime());
            sb.append("\nerror code : ");
            sb.append(location.getLocType());
            sb.append("\nlatitude : ");
            sb.append(location.getLatitude());
            sb.append("\nlontitude : ");
            sb.append(location.getLongitude());
            sb.append("\nradius : ");
            sb.append(location.getRadius());
            if (location.getLocType() == BDLocation.TypeGpsLocation) {// GPS定位结果
                sb.append("\nspeed : ");
                sb.append(location.getSpeed());// 单位：公里每小时
                sb.append("\nsatellite : ");
                sb.append(location.getSatelliteNumber());
                sb.append("\nheight : ");
                sb.append(location.getAltitude());// 单位：米
                sb.append("\ndirection : ");
                sb.append(location.getDirection());// 单位度
                sb.append("\naddr : ");
                sb.append(location.getAddrStr());
                sb.append("\ndescribe : ");
                sb.append("gps定位成功");

            } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {// 网络定位结果
                sb.append("\naddr : ");
                sb.append(location.getAddrStr());
                //运营商信息
                sb.append("\noperationers : ");
                sb.append(location.getOperators());
                sb.append("\ndescribe : ");
                sb.append("网络定位成功");
            } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {// 离线定位结果
                sb.append("\ndescribe : ");
                sb.append("离线定位成功，离线定位结果也是有效的");
            } else if (location.getLocType() == BDLocation.TypeServerError) {
                sb.append("\ndescribe : ");
                sb.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");
            } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
                sb.append("\ndescribe : ");
                sb.append("网络不同导致定位失败，请检查网络是否通畅");
            } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
                sb.append("\ndescribe : ");
                sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
            }
            sb.append("\nlocationdescribe : ");
            sb.append(location.getLocationDescribe());// 位置语义化信息
            List<Poi> list = location.getPoiList();// POI数据
            if (list != null) {
                sb.append("\npoilist size = : ");
                sb.append(list.size());
                for (Poi p : list) {
                    sb.append("\npoi= : ");
                    sb.append(p.getId() + " " + p.getName() + " " + p.getRank());
                }
            }
          System.out.println(sb.toString());
        }

    }

}

