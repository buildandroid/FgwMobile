package holder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.fgwoa.fgwmobile.R;
import com.unnamed.b.atv.model.TreeNode;

/**
 * Created by jim on 16/6/7.
 */
public class Category2Holder extends TreeNode.BaseNodeViewHolder<Category2Holder.Category2> {

    public Category2Holder(Context context){
        super(context);
    }

    public static class Category2{
        public String name;

        public Category2(String category){
            name = category;
        }
    }

    @Override
    public View createNodeView(TreeNode node, Category2 value) {
        View rootView = LayoutInflater.from(context).inflate(R.layout.category, null, false);
        TextView textView = (TextView)rootView.findViewById(R.id.category_tree_item);
        textView.setText(value.name);
        return rootView;
    }
}
