//package com.example.marketplanner
//
//import androidx.appcompat.app.AppCompatActivity
//import android.os.Bundle
//import androidx.databinding.DataBindingUtil
//import android.view.View
//import androidx.drawerlayout.widget.DrawerLayout
//import androidx.navigation.findNavController
//import androidx.navigation.ui.AppBarConfiguration
//import androidx.navigation.ui.NavigationUI
//import androidx.recyclerview.widget.LinearLayoutManager
//import androidx.recyclerview.widget.RecyclerView
//import com.example.marketplanner.adapters.ViewPagerAdapter
//import com.example.marketplanner.databinding.ActivityMainBinding
//
//class MainActivityPlain : AppCompatActivity() {
//    private lateinit var drawerLayout: DrawerLayout
//    private lateinit var appBarConfiguration: AppBarConfiguration
//    private var companies = ArrayList<String>()
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        @Suppress("UNUSED_VARIABLE")
//        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
//        drawerLayout = binding.drawerLayout
//
//        val navController = this.findNavController(R.id.navHostFragment)
//        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout)
//
//        appBarConfiguration = AppBarConfiguration(navController.graph, drawerLayout)
//        NavigationUI.setupWithNavController(binding.navView, navController)
//
//
////        setInitialData()
//    }
//
//    override fun onSupportNavigateUp(): Boolean {
//        val navController = this.findNavController(R.id.navHostFragment)
//        return NavigationUI.navigateUp(navController, appBarConfiguration)
//    }
//
////    private fun setInitialData(){
////        companies.add("Google")
////        companies.add("Apple")
////        companies.add("Amazon")
////    }
//}
