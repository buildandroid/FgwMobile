package gwQuery;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import java.util.List;

import bean.GwAttachs;
import bean.GwSignInfo;

/**
 * Created by wangxiao on 16/6/6.
 */
public class aaaActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        List<GwAttachs> gwAttachs1 = (List<GwAttachs>) intent.getSerializableExtra("gwAttachs");
//        List<GwForm> gwForm1 = (List<GwForm>) intent.getSerializableExtra("gwForm");
        List<GwSignInfo> gwSignInfo1 = (List<GwSignInfo>) intent.getSerializableExtra("gwSignInfo");
        final String ret = intent.getStringExtra("ret");

        for (int i = 0; i < gwAttachs1.size(); i++){
            GwAttachs attachs = gwAttachs1.get(i);
            String fileId = attachs.getROWGUID();
        }

//        for (int i = 0; i < gwForm1.size(); i++){
//            GwForm form = gwForm1.get(i);
//        }

        for (int i = 0; i < gwSignInfo1.size(); i++){
            GwSignInfo signInfo = gwSignInfo1.get(i);
        }
    }
}
