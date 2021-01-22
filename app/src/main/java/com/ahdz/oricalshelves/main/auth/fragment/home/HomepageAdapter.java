package com.ahdz.oricalshelves.main.auth.fragment.home;

import android.content.Context;
import android.graphics.Outline;
import android.os.Build;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.ahdz.oricalshelves.R;
import com.ahdz.oricalshelves.api.BaseResponse;
import com.ahdz.oricalshelves.api.UserApi;
import com.ahdz.oricalshelves.bean.BaiHuiData;
import com.ahdz.oricalshelves.bean.BannerModel;
import com.ahdz.oricalshelves.bean.Projects;
import com.ahdz.oricalshelves.bean.ShoreMain;
import com.ahdz.oricalshelves.bean.UrlData;
import com.ahdz.oricalshelves.databinding.ItemCardBinding;
import com.ahdz.oricalshelves.main.auth.activity.HomePagePresent;
import com.ahdz.oricalshelves.main.detail.MyWebviewActivity;
import com.ahdz.oricalshelves.util.GlideImageLoader;
import com.ahdz.oricalshelves.util.GlideUtil;
import com.ahdz.oricalshelves.view.VerticalScrollTextView;
import com.coorchice.library.SuperTextView;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class HomepageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<Projects> list = new ArrayList<>();
    private BaiHuiData baiHui=new BaiHuiData();

    private List<String> strings = new ArrayList<>();

    private List<BannerModel> bannerList = new ArrayList<>();


    public HomepageAdapter setBaiHui(BaiHuiData baiHui) {
        this.baiHui = baiHui;
        notifyDataSetChanged();
        return this;
    }

    public HomepageAdapter setBanner(List<BannerModel> bannerList){
        this.bannerList = bannerList;
        notifyDataSetChanged();
        return this;
    }

    public void setStrings(List<String> strings) {
        this.strings = strings;
        notifyDataSetChanged();
    }

    public void setList(List<Projects> list) {
        this.list = list;
        notifyDataSetChanged();
    }



    public HomepageAdapter(Context context) {
        this.context = context;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       /* if (viewType==TITLE){
            return new TitleVH(LayoutInflater.from(context).inflate(R.layout.item_replace_tv, parent, false));
        }*/
        return new ListVH(LayoutInflater.from(context).inflate(R.layout.item_card,parent,false));
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {

        /*if (viewHolder instanceof TitleVH) {
            TitleVH title = (TitleVH) viewHolder;
            //  adapter.setMuch(present.displayWithComma(baiHuiData.getQuota()+"") +".00");
            title.number.setText(FragmentHomePresent.displayWithComma(baiHui.getQuota()+"")+".00");

            if ( strings.size() > 0) {
                title.scrollText.setDataSource(strings);
                title.scrollText.startPlay();
            }

            List<String> mBannerList = new ArrayList<>();
            if (bannerList!=null && bannerList.size() > 0) {
                title.banner.setVisibility(View.VISIBLE);
                mBannerList.clear();
                for (int i = 0; i < bannerList.size(); i++) {
                    mBannerList.add(bannerList.get(i).getPictureUrl());
                }
                title.banner.setImages(mBannerList);
            }else {
                title.banner.setVisibility(View.GONE);
            }
            title.banner.setDelayTime(3500);
            title.banner.setImageLoader(new GlideImageLoader());
            title.banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);



            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                title.banner.setOutlineProvider(new ViewOutlineProvider() {
                    @Override
                    public void getOutline(View view, Outline outline) {
                        outline.setRoundRect(0, 0, view.getWidth(), view.getHeight(), 16f);
                    }
                });

            }


            title.banner.setOnBannerListener(posT -> {
                OnClickTo(bannerList.get(posT).getAppId()+"");
            });


            title.stv_l.setOnClickListener(v -> {
                if (TextUtils.isEmpty(baiHui.getUrl())){
                    return;
                }
                MyWebviewActivity.GoToService(context,baiHui.getUrl(),0,"");
            });


        } */
//        else if (viewHolder instanceof ListVH) {

            ListVH holder = (ListVH) viewHolder;
            int pos = position;

            GlideUtil.putHttpImg(list.get(pos).getIcon(),holder.binding.iconIv);
            holder.binding.iconNameTv.setText(list.get(pos).getName()+"");
            holder.binding.quotaTv.setText(list.get(pos).getLoanRange()+"");
            holder.binding.timeTv.setText(list.get(pos).getLoanTerm()+"");
            holder.binding.bottomTip2.setText(list.get(pos).getApplyers()+"");

//            holder.binding.bottomTip1.setText(list.get(pos).getRecommands()+"");
            holder.binding.itemTag.setText(list.get(pos).getRecommands()+"");



            if ( list.get(pos).getKeywords().size()>0){
                StringBuilder sb = new StringBuilder();
                for (int i = 0 ; i < list.get(pos).getKeywords().size() ; i++){
                    sb.append(list.get(pos).getKeywords().get(i)+" ");
                }
//                holder.binding.itemTag.setText(sb.toString()+"");
                holder.binding.bottomTip1.setText(sb.toString()+"");
            }



        holder.binding.cardApply.setOnClickListener(v -> {
            ShoreMain.showShopDetail(context, list.get(pos).getId()+"", holder.binding.iconNameTv.getText().toString());
        });

        holder.binding.stvTo.setOnClickListener(v -> {
            ShoreMain.showShopDetail(context, list.get(pos).getId()+"", holder.binding.iconNameTv.getText().toString());
        });
    }



 /*   private int TITLE = 0x02;
    private int OTHER = 0x03;
    @Override
    public int getItemViewType(int position) {
        if (position==0){
            return TITLE;
        }
        return OTHER;
    }*/

    @Override
    public int getItemCount() {
//        return list.size()+1;
        return list.size();
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
        Banner banner;
        VerticalScrollTextView scrollText;
        SuperTextView stv_l;
         View rl_vb;
        public TitleVH(@NonNull View itemView) {
            super(itemView);
            number = itemView.findViewById(R.id.tv_much);
            scrollText = itemView.findViewById(R.id.home_scroll_text);
            banner = itemView.findViewById(R.id.banner);
            stv_l = itemView.findViewById(R.id.stv_l);
            rl_vb = itemView.findViewById(R.id.rl_vb);
        }
    }

}
