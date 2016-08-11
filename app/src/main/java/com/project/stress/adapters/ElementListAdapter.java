package com.project.stress.adapters;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.project.stress.R;
import com.project.stress.models.DataHolder;
import com.project.stress.models.ListElement;

import java.util.ArrayList;
import java.util.List;

public class ElementListAdapter extends RecyclerView.Adapter<ElementListAdapter.ViewHolder> {

    private List<ListElement> elements = new ArrayList<>();
    private Context context;
    private TextView tvProgressStart;
    private ProgressBar progressBar;

    public ElementListAdapter(Context context, List<ListElement> elements, TextView tvProgressStart, ProgressBar progressBar) {
        this.context = context;
        this.elements = elements;

        this.tvProgressStart = tvProgressStart;
        this.progressBar = progressBar;
    }

    @Override
    public ElementListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_layout, parent,
                false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final ListElement ele = elements.get(position);
        final String title = ele.getTitle();
        final String description = ele.getDescription();
        final int selection = ele.getSelection();

        // Set text
        holder.tvTitle.setText(title);
        holder.tvDesciption.setText(description);

        String[][] options = ele.getOptions();
        int numOfOptions = options.length;
        for (int i = 0; i < numOfOptions; i++) {
            holder.rb[i].setVisibility(View.VISIBLE);
            holder.rb[i].setText(options[i][0]);
        }

        for (int i = numOfOptions; i < ListElement.MAX_OPTIONS; i++) {
            holder.rb[i].setVisibility(View.GONE);
        }

        holder.rb[selection].setChecked(true);

        // Set padding
        int paddingTop = (holder.tvTitle.getVisibility() != View.VISIBLE) ? 0 : holder.itemView.getContext().getResources()
                .getDimensionPixelSize(R.dimen.card_content_spacing);
        holder.tvDesciption.setPadding(holder.tvDesciption.getPaddingLeft(), paddingTop,
                holder.tvDesciption.getPaddingRight(), holder.tvDesciption.getPaddingBottom());


        holder.rb[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateSelection(position, 0);
            }
        });
        holder.rb[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateSelection(position, 1);
            }
        });
        holder.rb[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateSelection(position, 2);
            }
        });
        holder.rb[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateSelection(position, 3);
            }
        });

        holder.rb[4].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateSelection(position, 4);
            }
        });
        holder.rb[5].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateSelection(position, 5);
            }
        });
        holder.rb[6].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateSelection(position, 6);
            }
        });
        holder.rb[7].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateSelection(position, 7);
            }
        });

        holder.rb[8].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateSelection(position, 8);
            }
        });
        holder.rb[9].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateSelection(position, 9);
            }
        });
        holder.rb[10].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateSelection(position, 10);
            }
        });
        holder.rb[11].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateSelection(position, 11);
            }
        });

    }

    private void updateSelection(int position, int selection) {
        elements.get(position).setSelection(selection);
        if (!elements.get(position).isMarked()) {
            updateProgress();
        }
        elements.get(position).setMarked(true);
    }

    private void updateProgress() {
        DataHolder.numMarked++;
        tvProgressStart.setText(String.valueOf(DataHolder.numMarked));
        progressBar.setProgress(DataHolder.numMarked);
    }


    @Override
    public int getItemCount() {
        return elements.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public View view;
        public TextView tvTitle;
        public TextView tvDesciption;

        public RadioGroup radioGroup;

        public RadioButton[] rb;

        public ViewHolder(View itemView) {
            super(itemView);

            view = itemView;
            tvTitle = (TextView) itemView.findViewById(R.id.title);
            tvDesciption = (TextView) itemView.findViewById(R.id.description);

            radioGroup = (RadioGroup) itemView.findViewById(R.id.radioGroup);

            rb = new RadioButton[ListElement.MAX_OPTIONS];

            rb[0] = (RadioButton) itemView.findViewById(R.id.rb1);
            rb[1] = (RadioButton) itemView.findViewById(R.id.rb2);
            rb[2] = (RadioButton) itemView.findViewById(R.id.rb3);
            rb[3] = (RadioButton) itemView.findViewById(R.id.rb4);
            rb[4] = (RadioButton) itemView.findViewById(R.id.rb5);
            rb[5] = (RadioButton) itemView.findViewById(R.id.rb6);
            rb[6] = (RadioButton) itemView.findViewById(R.id.rb7);
            rb[7] = (RadioButton) itemView.findViewById(R.id.rb8);
            rb[8] = (RadioButton) itemView.findViewById(R.id.rb9);
            rb[9] = (RadioButton) itemView.findViewById(R.id.rb10);
            rb[10] = (RadioButton) itemView.findViewById(R.id.rb11);
            rb[11] = (RadioButton) itemView.findViewById(R.id.rb12);

        }
    }

}
