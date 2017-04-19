package android.com.skyh.base;
import android.com.skyh.R;
import android.com.skyh.adapter.DwbmListAdapter;
import android.com.skyh.entity.TwoTree;
import android.com.skyh.entity.TwoTreeData;
import android.com.skyh.service.RequestResult;
import android.com.skyh.tool.PrefName;
import android.com.skyh.tool.ProtocolType;
import android.com.skyh.tool.ToastUtil;
import android.com.skyh.until.ToolBar;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


/**
 *
 */
public class ChooseUserActivity extends BaseActivity {
    protected ToolBar toolBar;
    private ListView listView;
    private int index=-1;
    private RelativeLayout rl;
    private List<TwoTreeData> areaEntityList=new ArrayList<>();
    private DwbmListAdapter adapter;
    private int type;
    private int chooseType=0;

    private TextView sercherText;
    private EditText sercherEdit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dwbm_choose_list);
        initToolBar();
    }

    private void initToolBar(){
        toolBar = new ToolBar(this);
        toolBar.setToolbarCentreText("党委部门");
        toolBar.hideRightButton();
        toolBar.hideRightTextButton();
        ToolBar.left_btn.setImageResource(R.mipmap.fh);
        ToolBar.left_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        init();
    }

    private void init(){
        listView=(ListView)findViewById(R.id.house_listview);
     adapter=new DwbmListAdapter(this);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    Intent intent = new Intent();

    intent.putExtra("name", areaEntityList.get(position).getZzmc());
    intent.putExtra("index", areaEntityList.get(position).getZzdm());

    setResult(RESULT_OK, intent);
    finish();
}






        });

        areaList();
    }


    private void areaList(){
        showProgBar(getString(R.string.alert_info));

        String url = PrefName.DEFAULT_SERVER_URL +PrefName.LOGIN_TWO_SERVER_URL;

        sendRequest(url, null, PrefName.GET, ProtocolType.DWBMUSER, result);
    }
    RequestResult result=new RequestResult() {
        @Override
        public ProtocolType getType() {
            return ProtocolType.DWBMUSER;
        }

        @Override
        public void onSuccess(Object o) {
            super.onSuccess(o);
            hideProgBar();
            TwoTree areaResutl=(TwoTree) o;
             areaEntityList=areaResutl.gettUserlinkList();
            if(areaEntityList.size()<1){
                ToastUtil.show(ChooseUserActivity.this,"暂无数据");
            }
            adapter.setList(areaEntityList);
            adapter.notifyDataSetChanged();



        }
//        }

        @Override
        public void onFailure(CharSequence failure) {
            super.onFailure(failure);

            hideProgBar();

        }

        @Override
        public void OnErrorResult(CharSequence error) {
            super.OnErrorResult(error);
            hideProgBar();
        }
    };




}
