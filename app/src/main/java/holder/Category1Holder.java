package holder;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.unnamed.b.atv.model.TreeNode;

/**
 * Created by jim on 16/6/7.
 */
public class Category1Holder extends TreeNode.BaseNodeViewHolder<Category1Holder.Category1> {

    public Category1Holder(Context context){
        super(context);
    }

    public static class Category1{
        public String name;

        public Category1(String category){
            name = category;
        }
    }

    @Override
    public View createNodeView(TreeNode node, Category1 value) {
        TextView textView = new TextView(context);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        textView.setTextSize(20);
        textView.setLayoutParams(layoutParams);
        textView.setText(value.name);
        return textView;
    }
}
