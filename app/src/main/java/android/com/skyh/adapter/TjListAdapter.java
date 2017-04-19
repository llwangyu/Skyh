package android.com.skyh.adapter;

import android.com.skyh.R;
import android.com.skyh.entity.RsEntityResult;
import android.com.skyh.entity.TjData;
import android.com.skyh.until.StringUtils;
import android.com.skyh.until.TimeUtil;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2016-07-05.
 */
public class TjListAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private Context context;
    private List<RsEntityResult> list=new ArrayList<RsEntityResult>();;
    public TjListAdapter(Context context){
        this.context=context;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public List<RsEntityResult>getList() {
        return list;
    }

    public void setList(List<RsEntityResult> list) {
        this.list = list;
    }
    @Override
    public int getCount() {
        if(list!=null&&list.size()>0){
            return list.size();
        }else
            return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView==null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.activity_tj_list_item,
                    parent, false);
           viewHolder.title=(TextView)convertView.findViewById(R.id.title);
      //      viewHolder.zzName=(TextView)convertView.findViewById(R.id.zz_name);
            viewHolder.dateText=(TextView)convertView.findViewById(R.id.date_text);
//            viewHolder.isSh=(TextView)convertView.findViewById(R.id.is_sh);
//            viewHolder.dwmc=(TextView)convertView.findViewById(R.id.zwh);



            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if(list!=null&&list.size()>0){
//            viewHolder.title.setText(
//                    list.get(position).getYt1());
//            viewHolder.dwmc.setText(
//                    list.get(position).getDwmc());
//            if( list.get(position).issh()) {
//                viewHolder.isSh.setText("已审核");
//                viewHolder.isSh.setTextColor(0xFF1C1A1A);
//
//            }else {
//                viewHolder.isSh.setText("未审核");
//                viewHolder.isSh.setTextColor(0xFFE92323);
//            }
            viewHolder.title.setText(list.get(position).getZzmc());
                viewHolder.dateText.setText("时间:"+TimeUtil.longToDate(list.get(position).getZkrq()));





}


        return convertView;
    }



    private class ViewHolder {
        TextView title;
    //    TextView zzName;
        TextView dateText;
//        TextView isSh;
//        TextView dwmc;


    }

}
