package com.weiyian.android.mvvmlight.bindingadapter.recyclerview;

import android.annotation.SuppressLint;
import android.databinding.BindingAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.weiyian.android.mvvmlight.command.ReplyCommand;

import java.util.concurrent.TimeUnit;

import io.reactivex.subjects.PublishSubject;

/**
 * Created by kelin on 16-4-26.
 */
public class ViewBindingAdapter {
    
    @BindingAdapter(value = {"onScrollChangeCommand", "onScrollStateChangedCommand"}, requireAll = false)
    public static void onScrollChangeCommand(final RecyclerView recyclerView,
                                             final ReplyCommand<ScrollDataWrapper> onScrollChangeCommand,
                                             final ReplyCommand<Integer> onScrollStateChangedCommand) {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            private int state;
            
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (onScrollChangeCommand != null) {
                    onScrollChangeCommand.execute(new ScrollDataWrapper(dx, dy, state));
                }
            }
            
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                state = newState;
                if (onScrollStateChangedCommand != null) {
                    onScrollChangeCommand.equals(newState);
                }
            }
        });
        
    }
    
    @SuppressWarnings("unchecked")
    @BindingAdapter({"onLoadMoreCommand"})
    public static void onLoadMoreCommand(final RecyclerView recyclerView, final ReplyCommand<Integer> onLoadMoreCommand) {
        RecyclerView.OnScrollListener listener = new OnScrollListener(onLoadMoreCommand);
        recyclerView.addOnScrollListener(listener);
        
    }
    
    public static class OnScrollListener extends RecyclerView.OnScrollListener {
        
        private PublishSubject<Integer> methodInvoke = PublishSubject.create();
        
        private ReplyCommand<Integer> onLoadMoreCommand;
        
        @SuppressLint("CheckResult")
        public OnScrollListener(ReplyCommand<Integer> onLoadMoreCommand) {
            this.onLoadMoreCommand = onLoadMoreCommand;
            methodInvoke.throttleFirst(1, TimeUnit.SECONDS)
                    .subscribe(onLoadMoreCommand::execute);
        }
        
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
            int visibleItemCount = layoutManager.getChildCount();
            Log.d("TAG", "[ViewBindingAdapter] [OnScrollListener] [onScrolled] visibleItemCount = " + visibleItemCount);
            int totalItemCount = layoutManager.getItemCount();
            Log.d("TAG", "[ViewBindingAdapter] [OnScrollListener] [onScrolled] totalItemCount = " + totalItemCount);
            int pastVisiblesItems = layoutManager.findFirstVisibleItemPosition();
            Log.d("TAG", "[ViewBindingAdapter] [OnScrollListener] [onScrolled] pastVisiblesItems = " + pastVisiblesItems);
            if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                Log.d("TAG", "[ViewBindingAdapter] [OnScrollListener] [onScrolled] visibleItemCount + pastVisiblesItems = " + (visibleItemCount + pastVisiblesItems));
                Log.d("TAG", "[ViewBindingAdapter] [OnScrollListener] [onScrolled] visibleItemCount + pastVisiblesItems >=  totalItemCount = " + (visibleItemCount + pastVisiblesItems >= totalItemCount));
                if (onLoadMoreCommand != null) {
                    methodInvoke.onNext(recyclerView.getAdapter().getItemCount());
                }
            }
        }
        
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }
        
    }
    
    public static class ScrollDataWrapper {
        
        public float scrollX;
        public float scrollY;
        public int state;
        
        public ScrollDataWrapper(float scrollX, float scrollY, int state) {
            this.scrollX = scrollX;
            this.scrollY = scrollY;
            this.state = state;
        }
        
    }
    
}
