package com.ahdz.oricalshelves.main.auth.fragment.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.ahdz.oricalshelves.R;
import com.ahdz.oricalshelves.bean.Projects;
import com.ahdz.oricalshelves.bean.ShoreMain;
import com.ahdz.oricalshelves.databinding.ItemCardBinding;
import com.ahdz.oricalshelves.util.GlideUtil;
import com.ahdz.oricalshelves.view.VerticalScrollTextView;

import java.util.ArrayList;
import java.util.List;

public class HomepageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<Projects> list = new ArrayList<>();
    private String much = "0";

    private List<String> strings = new ArrayList<>();

    public void setStrings(List<String> strings) {
        this.strings = strings;
        notifyDataSetChanged();
    }

    public void setList(List<Projects> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void setMuch(String much) {
        this.much = much;
        notifyDataSetChanged();
    }

    public HomepageAdapter(Context context) {
        this.context = context;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType==TITLE){
            return new TitleVH(LayoutInflater.from(context).inflate(R.layout.item_replace_tv, parent, false));
        }
        return new ListVH(LayoutInflater.from(context).inflate(R.layout.item_card,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {

        if (viewHolder instanceof TitleVH) {
            TitleVH title = (TitleVH) viewHolder;
            title.number.setText(much);

            if ( strings.size() > 0) {
                title.scrollText.setDataSource(strings);
                title.scrollText.startPlay();
            }

        } else if (viewHolder instanceof ListVH) {
            ListVH holder = (ListVH) viewHolder;
            int pos = position - 1;

            GlideUtil.putHttpImg(list.get(pos).getIcon(),holder.binding.iconIv);
            holder.binding.iconNameTv.setText(list.get(pos).getName()+"");

            holder.binding.bottomTip2.setText(list.get(pos).getApplyers()+"");
            holder.binding.quotaTv.setText(list.get(pos).getLoanRange()+"");
            holder.binding.timeTv.setText(list.get(pos).getLoanTerm()+"");
            holder.binding.bottomTip1.setText(list.get(pos).getRecommands()+"");

            holder.binding.stvTo.setOnClickListener(v -> {
                ShoreMain.showShopDetail(context, list.get(pos).getId()+"", holder.binding.iconNameTv.getText().toString());
            });

            if ( list.get(pos).getKeywords().size()>0){
                StringBuilder sb = new StringBuilder();
                for (int i = 0 ; i < list.get(pos).getKeywords().size() ; i++){
                    sb.append(list.get(pos).getKeywords().get(i)+" ");
                }
                holder.binding.itemTag.setText(sb.toString()+"");
            }
        }

        // Projects
    }

    private int TITLE = 0x02;
    private int OTHER = 0x03;
    @Override
    public int getItemViewType(int position) {
        if (position==0){
            return TITLE;
        }
        return OTHER;
    }

    @Override
    public int getItemCount() {
        return list.size()+1;
    }

    class ListVH extends RecyclerView.ViewHolder {
        ItemCardBinding binding;
        public ListVH(@NonNull View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);

        }
    }


    class TitleVH extends RecyclerView.ViewHolder {
        TextView number;
        VerticalScrollTextView scrollText;
        public TitleVH(@NonNull View itemView) {
            super(itemView);
            number = itemView.findViewById(R.id.tv_much);
            scrollText = itemView.findViewById(R.id.home_scroll_text);
        }
    }

}
