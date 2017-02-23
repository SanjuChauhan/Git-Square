package sanju.com.gitsquare.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.text.Html;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import sanju.com.gitsquare.R;
import sanju.com.gitsquare.controller.CircleTransform;
import sanju.com.gitsquare.model.Contributor;

/**
 * Created by ehs on 15/12/16.
 */

public class ContributorsAdapter extends BaseAdapter {

    ArrayList<Contributor> arrayList;

    Context context;

    public ContributorsAdapter(Activity activity, ArrayList<Contributor> arrayList) {
        this.arrayList = arrayList;
        this.context = activity;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
            view = inflater.inflate(R.layout.contributor_item, viewGroup, false);
        }
        Contributor orderStatusModel = arrayList.get(i);

        ImageView img_avtar_ = (ImageView) view.findViewById(R.id.img_avtar_);
        TextView text_login = (TextView) view.findViewById(R.id.text_login);
        TextView text_repos_url = (TextView) view.findViewById(R.id.text_repos_url);
        TextView text_contribution = (TextView) view.findViewById(R.id.text_contribution);

        Picasso.with(context).load(orderStatusModel.getAvatar_url()).transform(new CircleTransform())
                .placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher).into(img_avtar_);

//        String linkText = "<a> href=" +orderStatusModel.getRepos_url() + ">Contributed Repos</a> .";
        Spanned linkText = Html.fromHtml(
                "<a href=\""+orderStatusModel.getRepos_url()+"\">Contributed Repos</a> ");
        text_repos_url.setText(linkText);
        text_repos_url.setTextColor(Color.parseColor("#000000"));
        text_repos_url.setMovementMethod(LinkMovementMethod.getInstance());

        text_login.setText(orderStatusModel.getLogin());
//        text_repos_url.setText(orderStatusModel.getRepos_url());
        text_contribution.setText("Contributions :"+orderStatusModel.getContributions());

        return view;
    }
}
