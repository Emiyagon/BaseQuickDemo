package com.ahdz.oricalshelves.main.auth.fragment.shop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.ahdz.oricalshelves.R;
import com.ahdz.oricalshelves.bean.Projects;
import com.ahdz.oricalshelves.bean.ShoreMain;
import com.ahdz.oricalshelves.databinding.ItemCardBinding;
import com.ahdz.oricalshelves.main.auth.fragment.home.HomepageAdapter;
import com.ahdz.oricalshelves.util.GlideUtil;

import java.util.ArrayList;
import java.util.List;

public class MallAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<Projects> list = new ArrayList<>();
    public MallAdapter(Context context) {
        this.context = context;
    }

    public void setList(List<Projects> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType==TITLE){
            return new Title(LayoutInflater.from(context).inflate(R.layout.item_top, null, false));
        }
        return new MallVH(LayoutInflater.from(context).inflate(R.layout.item_card,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder instanceof Title) {
            Title title = (Title) viewHolder;

        } else if (viewHolder instanceof MallVH) {
            MallVH holder = (MallVH) viewHolder;
            int pos = position - 1;

            GlideUtil.putHttpImg(list.get(pos).getIcon(),holder.binding.iconIv);
            holder.binding.iconNameTv.setText(list.get(pos).getName()+"");

            holder.binding.bottomTip2.setText(list.get(pos).getApplyers()+"");
            holder.binding.quotaTv.setText(list.get(pos).getLoanRange()+"");
            holder.binding.timeTv.setText(list.get(pos).getLoanTerm()+"");
            holder.binding.bottomTip1.setText(list.get(pos).getRecommands()+"");

            holder.binding.cardApply.setOnClickListener(v -> {
                ShoreMain.showShopDetail(context, list.get(pos).getId()+"",holder.binding.iconNameTv.getText().toString());
            });
            holder.binding.stvTo.setOnClickListener(v -> {
                ShoreMain.showShopDetail(context, list.get(pos).getId()+"",holder.binding.iconNameTv.getText().toString());
            });

            if ( list.get(pos).getKeywords().size()>0){
                StringBuilder sb = new StringBuilder();
                for (int i = 0 ; i < list.get(pos).getKeywords().size() ; i++){
                    sb.append(list.get(pos).getKeywords().get(i)+" ");
                }
                holder.binding.itemTag.setText(sb.toString()+"");
            }
        }
    }

    @Override
    public int getItemCount() {
        return list.size() + 1;
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

    class Title extends RecyclerView.ViewHolder {

        public Title(@NonNull View itemView) {
            super(itemView);
        }
    }

    class MallVH extends RecyclerView.ViewHolder {
        ItemCardBinding binding;
        public MallVH(@NonNull View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }
    }

}
