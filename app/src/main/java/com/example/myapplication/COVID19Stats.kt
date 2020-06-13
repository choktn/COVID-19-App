package com.example.myapplication

import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.widget.Filter
import android.widget.Filterable
import android.widget.SearchView
import kotlin.collections.ArrayList

class StatsFragment : Fragment(){
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.activity_stats,container,false)

        val GlobalText = view.findViewById(R.id.GlobalText) as TextView
        val NewConfirmed = view.findViewById(R.id.GlobalNewConfirmed) as TextView
        val TotalConfirmed = view.findViewById(R.id.GlobalTotalConfirmed) as TextView
        val NewDeaths = view.findViewById(R.id.GlobalNewDeaths) as TextView
        val TotalDeaths = view.findViewById(R.id.GlobalTotalDeaths) as TextView
        val NewRecovered = view.findViewById(R.id.GlobalNewRecovered) as TextView
        val TotalRecovered = view.findViewById(R.id.GlobalTotalRecovered) as TextView
        val DateUpdated = view.findViewById(R.id.DateUpdated) as TextView
        val Source = view.findViewById(R.id.Source) as TextView

        GlobalText.setText("Global")
        NewConfirmed.setText(globalStats?.NewConfirmed  + " new confirmed cases")
        TotalConfirmed.setText(globalStats?.TotalConfirmed  + " total confirmed cases")
        NewDeaths.setText(globalStats?.NewDeaths  + " new deaths")
        TotalDeaths.setText(globalStats?.TotalDeaths  + " total deaths")
        NewRecovered.setText(globalStats?.NewRecovered  + " newly recovered")
        TotalRecovered.setText(globalStats?.TotalRecovered  + " total recovered")
        var datetime = countryList[0].Date.split("T", "Z")
        DateUpdated.setText("Last Updated: " + datetime[0] + " at " + datetime[1])
        Source.setText("Data API: https://covid19api.com/")

        val recyclerView: RecyclerView = view.findViewById(R.id.COVIDRecycler)
        recyclerView.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL,false)
        val adapter = countryList?.let { CustomAdapter(countryList = it) }
        recyclerView.adapter = adapter
        val search = view.findViewById<SearchView>(R.id.searchView)

        search.setOnQueryTextListener(object:SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                 if (adapter != null) {
                    adapter.filter.filter(newText)
                }
                return false
            }
        })
        return view
    }
}

class CustomAdapter(val countryList: ArrayList<CountryStats>) :
    RecyclerView.Adapter<CustomAdapter.ViewHolder>(), Filterable {
    var countryFilterList = ArrayList<CountryStats>()

    init {
        countryFilterList = countryList
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val Country = itemView.findViewById(R.id.Country) as TextView
        val NewConfirmed = itemView.findViewById(R.id.NewConfirmed) as TextView
        val TotalConfirmed = itemView.findViewById(R.id.TotalConfirmed) as TextView
        val NewDeaths = itemView.findViewById(R.id.NewDeaths) as TextView
        val TotalDeaths = itemView.findViewById(R.id.TotalDeaths) as TextView
        val NewRecovered = itemView.findViewById(R.id.NewRecovered) as TextView
        val TotalRecovered = itemView.findViewById(R.id.TotalRecovered) as TextView
        val subItem = itemView.findViewById(R.id.stats) as View
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent?.context)
            .inflate(R.layout.recycler_items, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return countryFilterList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val country: CountryStats = countryFilterList[position]

        var expanded = country.expanded
        if (expanded){
            holder.subItem.setVisibility(View.VISIBLE)
        }
        else {
            holder.subItem.setVisibility(View.GONE)
        }

        holder.Country.text = country.Country
        holder.NewConfirmed.text = country.NewConfirmed + " new confirmed cases"
        holder.TotalConfirmed.text = country.TotalConfirmed + " total confirmed cases"
        holder.NewDeaths.text = country.NewDeaths + " new deaths"
        holder.TotalDeaths.text = country.TotalDeaths + " total deaths"
        holder.NewRecovered.text = country.NewRecovered + " newly recovered"
        holder.TotalRecovered.text = country.TotalRecovered + " total recovered"

        holder.itemView.setOnClickListener {
            var expanded = country.expanded

            country.expanded = !expanded

            notifyItemChanged(position)
        }
    }

    override fun getFilter(): Filter {
        return object: Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    countryFilterList = countryList
                }
                else {
                    val resultList = ArrayList<CountryStats>()
                    for (country in countryList) {
                        if (country.Country.contains(charSearch, ignoreCase = true)) {
                            resultList.add(country)
                        }
                    }
                    countryFilterList = resultList
                }

                val filterResults = FilterResults()
                filterResults.values = countryFilterList
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                countryFilterList = results?.values as ArrayList<CountryStats>
                notifyDataSetChanged()
            }
        }
    }
}





