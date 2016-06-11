package holder;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.unnamed.b.atv.model.TreeNode;

/**
 * Created by jim on 16/6/7.
 */
public class Category3Holder extends TreeNode.BaseNodeViewHolder<Category3Holder.Category3> {

    public Category3Holder(Context context){
        super(context);
    }

    public static class Category3{
        public String name;
        public int page;

        public Category3(String category, int pageNumber){
            name = category;
            page = pageNumber;
        }
    }

    @Override
    public View createNodeView(TreeNode node, Category3 value) {
        TextView textView = new TextView(context);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        textView.setTextSize(20);
        textView.setLayoutParams(layoutParams);
        textView.setText(value.name);
        return textView;
    }
}

