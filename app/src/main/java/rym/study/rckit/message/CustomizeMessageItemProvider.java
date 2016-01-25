package rym.study.rckit.message;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import io.rong.imkit.model.ProviderTag;
import io.rong.imkit.model.UIMessage;
import io.rong.imkit.util.AndroidEmoji;
import io.rong.imkit.widget.provider.IContainerItemProvider;
import io.rong.imlib.model.Message;
import rym.study.rckit.R;

@ProviderTag(messageContent = CustomizeMessage.class, showPortrait = false, centerInHorizontal = true)
public class CustomizeMessageItemProvider extends IContainerItemProvider.MessageProvider<CustomizeMessage> {

    class ViewHolder {
        TextView message;
    }

    @Override
    public View newView(Context context, ViewGroup group) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_customize_message, null);
        ViewHolder holder = new ViewHolder();
//        holder.message = (TextView) view.findViewById(R.id.text1);
        view.setTag(holder);
        return view;
    }

    @Override
    public void bindView(View v, int position, CustomizeMessage content, UIMessage message) {
        ViewHolder holder = (ViewHolder) v.getTag();

//        if (message.getMessageDirection() == Message.MessageDirection.SEND) {
//            holder.message.setBackgroundResource(io.rong.imkit.R.drawable.rc_ic_bubble_right);
//        } else {
//            holder.message.setBackgroundResource(io.rong.imkit.R.drawable.rc_ic_bubble_left);
//        }
//        holder.message.setText(content.getContent());
//        AndroidEmoji.ensure((Spannable) holder.message.getText());
    }

    @Override
    public Spannable getContentSummary(CustomizeMessage data) {
        return new SpannableString("这是一条自定义消息CustomizeMessage");
    }

    @Override
    public void onItemClick(View view, int position, CustomizeMessage content, UIMessage message) {

    }

    @Override
    public void onItemLongClick(View view, int position, CustomizeMessage content, UIMessage message) {

    }

}