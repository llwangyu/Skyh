package android.com.skyh.adapter;
import android.com.skyh.R;
import android.com.skyh.entity.TwoTreeData;
import android.com.skyh.until.StringUtils;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2016-07-05.
 */
public class DwbmListAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private Context context;
   private int num=0;
    private List<TwoTreeData> list=new ArrayList<TwoTreeData>();;
    public DwbmListAdapter(Context context){
        this.context=context;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public List<TwoTreeData>getList() {
        return list;
    }

    public void setList(List<TwoTreeData> list) {
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
        final ViewHolder viewHolder;
        if(convertView==null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.activity_dwbm_list_row,
                    parent, false);
            viewHolder.name=(TextView)convertView.findViewById(R.id.area_tv);




            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if(list!=null&&list.size()>0){
        if (StringUtils.isNull(list.get(position).getZzmc())){
            viewHolder.name.setText(
                    "暂无名称");
        }else {
            viewHolder.name.setText(
                    list.get(position).getZzmc());
        }

        }
        return convertView;
    }



    private class ViewHolder {

        public TextView name;



    }

}
