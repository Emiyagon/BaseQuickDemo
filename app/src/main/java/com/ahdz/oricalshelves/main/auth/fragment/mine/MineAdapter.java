package com.ahdz.oricalshelves.main.auth.fragment.mine;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.ahdz.oricalshelves.R;
import com.ahdz.oricalshelves.bean.Projects;
import com.ahdz.oricalshelves.bean.ShoreMain;
import com.ahdz.oricalshelves.databinding.ItemCardBinding;
import com.ahdz.oricalshelves.main.auth.activity.SildeActivity;
import com.ahdz.oricalshelves.main.auth.activity.setting.SettingActivity;
import com.ahdz.oricalshelves.util.GlideUtil;

import java.util.ArrayList;
import java.util.List;

public class MineAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<Projects> list = new ArrayList<>();

    private String telePhone = "";

    private boolean isDk = false;

    public void setDk(boolean dk) {
        isDk = dk;
        notifyDataSetChanged();
    }

    public void setTelePhone(String telePhone) {
        this.telePhone = telePhone;
        notifyDataSetChanged();
    }

    public MineAdapter(Context context) {
        this.context = context;
    }

    public void setList(List<Projects> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TITLE) {
            return new SettingVH(LayoutInflater.from(context).inflate(R.layout.item_setting, null, false));
        }
        return new ListVH(LayoutInflater.from(context).inflate(R.layout.item_card,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {

        if (viewHolder instanceof SettingVH) {
            SettingVH setting = (SettingVH) viewHolder;
            setting.tele.setText(telePhone);
            setting.ll_dk.setVisibility(isDk?View.VISIBLE:View.GONE);
            setting.ll_dk.setOnClickListener(v -> {
                context.startActivity(new Intent(context, SildeActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
            });

            setting.setting.setOnClickListener(v -> {
                context.startActivity(new Intent(context, SettingActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
            });
        } else if (viewHolder instanceof ListVH) {
            ListVH holder = (ListVH) viewHolder;
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
        return list.size()+1;
    }

    private int TITLE = 0x06;
    private int EXTRA = 0x15;

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TITLE;
        } else {
            return EXTRA;
        }
    }


    class ListVH extends RecyclerView.ViewHolder {
        ItemCardBinding binding;
        public ListVH(@NonNull View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);

        }
    }

    class SettingVH extends RecyclerView.ViewHolder {
        ImageView icon;
        TextView tele;
        View setting,ll_dk;
        public SettingVH(@NonNull View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.icon);
            tele = itemView.findViewById(R.id.tele);
            setting = itemView.findViewById(R.id.img_setting);
            ll_dk = itemView.findViewById(R.id.ll_dk);
        }
    }

}
