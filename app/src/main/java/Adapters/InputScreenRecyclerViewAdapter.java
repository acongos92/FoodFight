package Adapters;
import com.BackendCode.Voting;
import com.foodfight.R;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.logging.Logger;

/**
 * Created by andy on 10/21/2017.
 */

public class InputScreenRecyclerViewAdapter extends RecyclerView.Adapter<InputScreenRecyclerViewAdapter.ClassViewHolder> {
    /**
     * nested interface define how click listeners will behave within this view
     */
    public interface InputScreenRecyclerViewAdapater {
        void onSemesterGPAClick(String semesterItemClicked);
    }

    //adapter variable declarations
    private static final Logger LOGGER = Logger.getLogger("DisplaySemesterGPAAdapter Logger");
    private Context mContext;
    private InputScreenRecyclerViewAdapter.InputScreenRecyclerViewAdapater clickListener;

    /**
     * Constructor
     *
     * Click Listener is defined in interface and implemented at the end of the class
     *
     */
    public DisplaySemesterGPAAdapter(Context context, Voting voting, InputScreenRecyclerViewAdapater  listener) {
        this.mContext = context;
        this.clickListener = listener;
        this.semestersAndGPA = list;
    }



    /**
     * we override onCreateViewHolder in order to use our own view definitions in implementation
     * by default implementation this would be incorrectly done
     * @param viewGroup
     * @param viewType
     * @return
     */
    @Override
    public DisplaySemesterGPAAdapter.SemesterAndGPAViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();

        int layoutIdForListItem = R.layout.homescreen_recycler_view_content;
        LayoutInflater inflater = LayoutInflater.from(context);

        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        DisplaySemesterGPAAdapter.SemesterAndGPAViewHolder viewHolder = new DisplaySemesterGPAAdapter.SemesterAndGPAViewHolder(view);

        return viewHolder;
    }

    /**
     * we override onBindViewHolder in order to properly assign data to be displayed in the view
     * using the class cursor object
     * @param holder the view holder we want to put data in
     * @param position the position data will be assigned to in the scrollable list
     */
    @Override
    public void onBindViewHolder(DisplaySemesterGPAAdapter.SemesterAndGPAViewHolder holder, int position) {
        LOGGER.info("DisplaySemesterGPAAdapter start onBindViewHolder");
        if ((semestersAndGPA.size() < position)){
            return;
        }
        String name = semestersAndGPA.get(position).getSemesterOrClassName();
        double gpa = semestersAndGPA.get(position).calculateGPA();
        String gpas = Double.toString(gpa);

        String nameString = APPEND_SEMESTER_STRING + name;
        String gradeString;
        holder.semesterNameAndGpaNameView.setText(nameString);
        //control display of gpa digits
        if(gpas.length() > 4){
            gradeString = APPEND_GRADE_STRING  + gpas.substring(0,4);
            holder.semesterNameAndGpaGPAview.setText(gradeString);
        }else {
            gradeString = APPEND_GRADE_STRING + gpas;
            holder.semesterNameAndGpaGPAview.setText(gradeString);
        }
    }

    /**
     * we override getItemCount again so we can use the class cursor object to properly define
     * the total number of items in the list we plan to display
     * @return items in the cursor
     */
    @Override
    public int getItemCount() {
        return semestersAndGPA.size();
    }

    /**
     * nested class implements the onClickListener so we can define what is done on clicks to items.
     * this viewHolder essentially tracks each individual text view column which contains our data
     * (so in this view holder its tracking just the semester names but could of course hold
     *  multiple views)
     */
    class SemesterAndGPAViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        //define the views this viewHolder will be tracking
        protected TextView userSubmissionView;

        /**
         * constructor for the view holder
         * @param itemView the view which will hold an item of data in the recycler view (single row)
         */
        public SemesterAndGPAViewHolder(View itemView) {

            super(itemView);

            userSubmissionView = (TextView) itemView.findViewById(R.id.tv_submission_name;
            itemView.setOnClickListener(this);

        }


        @Override
        public void onClick(View view){

            LOGGER.info("DisplaySemesterGPAAdapter RECYCLERVIEWCLICKLISTENER start onClick ");
            int clickedPosition = getAdapterPosition();
            //procceses out the the string semester: leaving only the semester name for use by the click listener
            String semesterName = String.valueOf(semesterNameAndGpaNameView.getText()).substring(APPEND_SEMESTER_STRING.length());
            clickListener.onSemesterGPAClick(semesterName);
        }


    }
}
