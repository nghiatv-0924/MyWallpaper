package com.sun.mywallpaper.ui.home

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.tabs.TabLayout
import com.mikepenz.iconics.IconicsColor
import com.mikepenz.iconics.IconicsDrawable
import com.mikepenz.iconics.IconicsSize
import com.mikepenz.materialdrawer.AccountHeader
import com.mikepenz.materialdrawer.AccountHeaderBuilder
import com.mikepenz.materialdrawer.Drawer
import com.mikepenz.materialdrawer.DrawerBuilder
import com.mikepenz.materialdrawer.model.DividerDrawerItem
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem
import com.mikepenz.materialdrawer.model.ProfileDrawerItem
import com.mikepenz.materialdrawer.model.ProfileSettingDrawerItem
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem
import com.mikepenz.materialdrawer.model.interfaces.IProfile
import com.mikepenz.materialdrawer.util.AbstractDrawerImageLoader
import com.mikepenz.materialdrawer.util.DrawerImageLoader
import com.mikepenz.materialdrawer.util.DrawerUIUtils
import com.mikepenz.materialdrawer.util.DrawerUIUtils.getPlaceHolder
import com.sun.mywallpaper.MainActivity
import com.sun.mywallpaper.R
import com.sun.mywallpaper.base.BaseFragment
import com.sun.mywallpaper.base.FragmentInteractionListener
import com.sun.mywallpaper.databinding.FragmentHomeBinding
import com.sun.mywallpaper.util.Constants
import com.sun.mywallpaper.util.ThemeUtils
import com.sun.mywallpaper.util.Utils
import kotlinx.android.synthetic.main.fragment_home.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>(),
    Drawer.OnDrawerItemClickListener {

    override val layoutResource: Int = R.layout.fragment_home
    override val viewModel: HomeViewModel by viewModel()
    private var listener: OnHomeFragmentInteractionListener? = null

    private lateinit var pagerAdapter: PagerAdapter
    private lateinit var drawer: Drawer
    private lateinit var drawerHeader: AccountHeader
    private lateinit var drawerItemAddAccount: ProfileSettingDrawerItem
    private lateinit var drawerItemViewProfile: ProfileSettingDrawerItem
    private lateinit var drawerItemManageAccount: ProfileSettingDrawerItem
    private lateinit var drawerItemLogout: ProfileSettingDrawerItem
    private val profile: IProfile
    private lateinit var profileDefault: ProfileDrawerItem
    private lateinit var itemFeaturedLatest: MenuItem
    private lateinit var itemFeaturedOldest: MenuItem
    private lateinit var itemFeaturedPopular: MenuItem
    private lateinit var itemNewLatest: MenuItem
    private lateinit var itemNewOldest: MenuItem
    private lateinit var itemNewPopular: MenuItem
    private lateinit var itemAll: MenuItem
    private lateinit var itemCurated: MenuItem
    private lateinit var itemFeatured: MenuItem
    private var newFragmentRecreated = false
    private var featuredFragmentRecreated = false
    private var collectionFragmentRecreated = false

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnHomeFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException("$context $ERROR_IMPLEMENT_FRAGMENT_INTERACTION_LISTENER")
        }
    }

    override fun onStart() {
        super.onStart()
    }

    override fun initComponents() {
        activity?.let { activity ->
            context?.let { context ->
                (activity as AppCompatActivity).setSupportActionBar(toolBar)
                activity.setTitle(getString(R.string.app_name))
                Utils.isStoragePermissionGranted(activity)

                profileDefault =
                    ProfileDrawerItem().withName(resources.getString(R.string.app_name))
                        .withEmail(resources.getString(R.string.drawer_app_description))
                        .withIcon(R.mipmap.ic_launcher_round)

                DrawerImageLoader.Companion.init(object : AbstractDrawerImageLoader() {

                    override fun set(
                        imageView: ImageView,
                        uri: Uri,
                        placeholder: Drawable,
                        tag: String?
                    ) {
                        Glide.with(context)
                            .load(uri)
                            .apply(RequestOptions().placeholder(placeholder))
                            .into(imageView)
                    }

                    override fun cancel(imageView: ImageView) {
                        if (!activity.isFinishing)
                            Glide.with(context).clear(imageView)
                    }

                    override fun placeholder(ctx: Context, tag: String?): Drawable {
                        return when (tag) {
                            DrawerImageLoader.Tags.PROFILE.name -> getPlaceHolder(ctx)

                            DrawerImageLoader.Tags.ACCOUNT_HEADER.name -> IconicsDrawable(ctx).iconText(
                                " "
                            )
                                .backgroundColor(IconicsColor.colorRes(R.color.primary_light))
                                .size(IconicsSize.dp(resources.getDimension(R.dimen.dp_56)))

                            "customUrlItem" -> IconicsDrawable(ctx).iconText(" ")
                                .backgroundColor(IconicsColor.colorRes(R.color.md_red_500))
                                .size(IconicsSize.dp(resources.getDimension(R.dimen.dp_56)))

                            else -> super.placeholder(ctx, tag)
                        }
                    }
                })

                drawerItemAddAccount =
                    ProfileSettingDrawerItem().withName(resources.getString(R.string.drawer_add_account))
                        .withIcon(ThemeUtils.getThemeAttrDrawable(context, R.attr.addIcon))
                        .withIdentifier(1).withOnDrawerItemClickListener(this)
                        .withTextColor(
                            ThemeUtils.getThemeAttrColor(
                                context,
                                R.attr.primaryTextColor
                            )
                        )
                drawerItemViewProfile =
                    ProfileSettingDrawerItem().withName(resources.getString(R.string.drawer_view_profile))
                        .withIcon(ThemeUtils.getThemeAttrDrawable(context, R.attr.profileIcon))
                        .withIdentifier(2).withOnDrawerItemClickListener(this)
                        .withTextColor(
                            ThemeUtils.getThemeAttrColor(
                                context,
                                R.attr.primaryTextColor
                            )
                        )
                drawerItemManageAccount =
                    ProfileSettingDrawerItem().withName(resources.getString(R.string.drawer_manage_account))
                        .withIcon(ThemeUtils.getThemeAttrDrawable(context, R.attr.settingsIcon))
                        .withIdentifier(3).withOnDrawerItemClickListener(this)
                        .withTextColor(
                            ThemeUtils.getThemeAttrColor(
                                context,
                                R.attr.primaryTextColor
                            )
                        )
                drawerItemLogout =
                    ProfileSettingDrawerItem().withName(getString(R.string.drawer_logout))
                        .withIcon(ThemeUtils.getThemeAttrDrawable(context, R.attr.cancelIcon))
                        .withIdentifier(4).withOnDrawerItemClickListener(this)
                        .withTextColor(
                            ThemeUtils.getThemeAttrColor(
                                context,
                                R.attr.primaryTextColor
                            )
                        )

                drawerHeader = AccountHeaderBuilder()
                    .withActivity(activity)
                    .withTranslucentStatusBar(true)
                    .withTextColor(ThemeUtils.getThemeAttrColor(context, R.attr.primaryTextColor))
                    .withProfileImagesClickable(false)
                    .withCurrentProfileHiddenInList(true)
                    .withSavedInstance(savedInstanceState)
                    .build()

                updateDrawerItems()

                drawer = DrawerBuilder()
                    .withActivity(activity)
                    .withTranslucentStatusBar(false)
                    .withActionBarDrawerToggle(true)
                    .withToolbar(toolBar)
                    .withDelayDrawerClickEvent(200)
                    .withAccountHeader(drawerHeader)
                    .addDrawerItems(
                        PrimaryDrawerItem().withName(resources.getString(R.string.drawer_new)).withIdentifier(
                            5
                        ).withIcon(
                            ThemeUtils.getThemeAttrDrawable(
                                context,
                                R.attr.newIcon
                            )
                        ).withTextColor(
                            ThemeUtils.getThemeAttrColor(
                                context,
                                R.attr.primaryTextColor
                            )
                        ).withSelectedTextColor(
                            ThemeUtils.getThemeAttrColor(
                                context,
                                R.attr.primaryTextColor
                            )
                        ),
                        PrimaryDrawerItem().withName(resources.getString(R.string.drawer_featured)).withIdentifier(
                            6
                        ).withIcon(
                            ThemeUtils.getThemeAttrDrawable(
                                context,
                                R.attr.hotIcon
                            )
                        ).withTextColor(
                            ThemeUtils.getThemeAttrColor(
                                context,
                                R.attr.primaryTextColor
                            )
                        ).withSelectedTextColor(
                            ThemeUtils.getThemeAttrColor(
                                context,
                                R.attr.primaryTextColor
                            )
                        ),
                        PrimaryDrawerItem().withName(resources.getString(R.string.drawer_collections)).withIdentifier(
                            7
                        ).withIcon(
                            ThemeUtils.getThemeAttrDrawable(
                                context,
                                R.attr.collectionsIcon
                            )
                        ).withTextColor(
                            ThemeUtils.getThemeAttrColor(
                                context,
                                R.attr.primaryTextColor
                            )
                        ).withSelectedTextColor(
                            ThemeUtils.getThemeAttrColor(
                                context,
                                R.attr.primaryTextColor
                            )
                        ),
                        DividerDrawerItem(),
                        PrimaryDrawerItem().withName(resources.getString(R.string.auto_wallpaper_title)).withIdentifier(
                            8
                        ).withIcon(
                            ThemeUtils.getThemeAttrDrawable(
                                context,
                                R.attr.autoWallpaperIcon
                            )
                        ).withSelectable(false).withTextColor(
                            ThemeUtils.getThemeAttrColor(
                                context,
                                R.attr.primaryTextColor
                            )
                        ),
                        DividerDrawerItem(),
                        PrimaryDrawerItem().withName(resources.getString(R.string.drawer_settings)).withIdentifier(
                            9
                        ).withIcon(
                            ThemeUtils.getThemeAttrDrawable(
                                context,
                                R.attr.settingsIcon
                            )
                        ).withSelectable(false).withTextColor(
                            ThemeUtils.getThemeAttrColor(
                                context,
                                R.attr.primaryTextColor
                            )
                        ),
                        PrimaryDrawerItem().withName(getString(R.string.drawer_about)).withIdentifier(
                            10
                        ).withIcon(
                            ThemeUtils.getThemeAttrDrawable(
                                context,
                                R.attr.infoIcon
                            )
                        ).withSelectable(false).withTextColor(
                            ThemeUtils.getThemeAttrColor(
                                context,
                                R.attr.primaryTextColor
                            )
                        )
                    )
                    .withOnDrawerItemClickListener(this)
                    .build()

                drawer.recyclerView.isVerticalScrollBarEnabled = false

                pagerAdapter = PagerAdapter(childFragmentManager)
                pagerAdapter.apply {
                    addFragment(NewFragment.newInstance("latest"), getString(R.string.main_new))
                    addFragment(
                        FeaturedFragment.newInstance("latest"),
                        getString(R.string.main_featured)
                    )
                    addFragment(
                        CollectionFragment.newInstance("Featured"),
                        getString(R.string.main_collections)
                    )
                }
                viewPager.apply {
                    adapter = pagerAdapter
                    offscreenPageLimit = 2
                }

                tabLayout.setupWithViewPager(viewPager)
                tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {

                    override fun onTabReselected(tab: TabLayout.Tab) {
                        viewPager.currentItem = tab.position
                        when {
                            tab.position == 0 -> drawer.setSelection(1)
                            tab.position == 1 -> drawer.setSelection(2)
                            else -> drawer.setSelection(3)
                        }
                    }

                    override fun onTabUnselected(tab: TabLayout.Tab?) {
                    }

                    override fun onTabSelected(tab: TabLayout.Tab?) {
                    }
                })

                fabUpload.setOnClickListener {
                    try {
                        val uri = Uri.parse(Constants.UNSPLASH_UPLOAD_URL)
                        val intent = Intent(Intent.ACTION_VIEW, uri)
                        if (intent.resolveActivity(activity.packageManager) != null)
                            startActivity(intent)
                        else
                            Toast.makeText(
                                context,
                                resources.getString(R.string.error),
                                Toast.LENGTH_SHORT
                            ).show()
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }

                /*mToolbar.setOnClickListener(v -> {
                Fragment fragment;
                switch (mViewPager.getCurrentItem()) {
                    case 0:
                    fragment = mNewFragmentRecreated ? getSupportFragmentManager().findFragmentById(R.id.new_container) : mPagerAdapter.getItem(0);
                    if (fragment instanceof NewFragment) {
                        ((NewFragment) fragment).scrollToTop();
                    }
                    break;
                    case 1:
                    fragment = mFeaturedFragmentRecreated ? getSupportFragmentManager().findFragmentById(R.id.featured_container) : mPagerAdapter.getItem(1);
                    if (fragment instanceof FeaturedFragment) {
                        ((FeaturedFragment) fragment).scrollToTop();
                    }
                    break;
                    case 2:
                    fragment = mCollectionFragmentRecreated ? getSupportFragmentManager().findFragmentById(R.id.collection_container) : mPagerAdapter.getItem(2);
                    if (fragment instanceof CollectionFragment) {
                        ((CollectionFragment) fragment).scrollToTop();
                    }
                    break;
                }          */
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.home, menu)

        itemFeaturedLatest = menu.findItem(R.id.menu_item_featured_latest)
        itemFeaturedOldest = menu.findItem(R.id.menu_item_featured_oldest)
        itemFeaturedPopular = menu.findItem(R.id.menu_item_featured_popular)
        itemNewLatest = menu.findItem(R.id.menu_item_new_latest)
        itemNewOldest = menu.findItem(R.id.menu_item_new_oldest)
        itemNewPopular = menu.findItem(R.id.menu_item_new_popular)
        itemAll = menu.findItem(R.id.menu_item_all)
        itemCurated = menu.findItem(R.id.menu_item_curated)
        itemFeatured = menu.findItem(R.id.menu_item_featured)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)

        when (viewPager.currentItem) {
            0 -> {
                itemFeaturedLatest.isVisible = false
                itemFeaturedOldest.isVisible = false
                itemFeaturedPopular.isVisible = false
                itemNewLatest.isVisible = true
                itemNewOldest.isVisible = true
                itemNewPopular.isVisible = true
                itemAll.isVisible = false
                itemCurated.isVisible = false
                itemFeatured.isVisible = false
            }

            1 -> {
                itemFeaturedLatest.isVisible = true
                itemFeaturedOldest.isVisible = true
                itemFeaturedPopular.isVisible = true
                itemNewLatest.isVisible = false
                itemNewOldest.isVisible = false
                itemNewPopular.isVisible = false
                itemAll.isVisible = false
                itemCurated.isVisible = false
                itemFeatured.isVisible = false
            }

            2 -> {
                itemFeaturedLatest.isVisible = false
                itemFeaturedOldest.isVisible = false
                itemFeaturedPopular.isVisible = false
                itemNewLatest.isVisible = false
                itemNewOldest.isVisible = false
                itemNewPopular.isVisible = false
                itemAll.isVisible = true
                itemCurated.isVisible = true
                itemFeatured.isVisible = true
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        /*FragmentTransaction transaction = getSupportFragmentManager ().beginTransaction();

        switch(item.getItemId()) {
            case android . R . id . home :
            onBackPressed();
            return true;
            case R . id . action_search :
            startActivity(new Intent (MainActivity.this, SearchActivity.class));
            return true;
            case R . id . sort_by :
            return true;
            case R . id . menu_item_featured_latest :
            transaction.replace(R.id.featured_container, FeaturedFragment.newInstance("latest"))
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
            mFeaturedFragmentRecreated = true;
            return true;
            case R . id . menu_item_featured_oldest :
            transaction.replace(R.id.featured_container, FeaturedFragment.newInstance("oldest"))
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
            mFeaturedFragmentRecreated = true;
            return true;
            case R . id . menu_item_featured_popular :
            transaction.replace(R.id.featured_container, FeaturedFragment.newInstance("popular"))
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
            mFeaturedFragmentRecreated = true;
            return true;
            case R . id . menu_item_new_latest :
            transaction.replace(R.id.new_container, NewFragment.newInstance("latest"))
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
            mNewFragmentRecreated = true;
            return true;
            case R . id . menu_item_new_oldest :
            transaction.replace(R.id.new_container, NewFragment.newInstance("oldest"))
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
            mNewFragmentRecreated = true;
            return true;
            case R . id . menu_item_new_popular :
            transaction.replace(R.id.new_container, NewFragment.newInstance("popular"))
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
            mNewFragmentRecreated = true;
            return true;
            case R . id . menu_item_all :
            transaction.replace(R.id.collection_container, CollectionFragment.newInstance("All"))
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
            mCollectionFragmentRecreated = true;
            return true;
            case R . id . menu_item_curated :
            transaction.replace(
                R.id.collection_container,
                CollectionFragment.newInstance("Curated")
            ).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
            mCollectionFragmentRecreated = true;
            return true;
            case R . id . menu_item_featured :
            transaction.replace(
                R.id.collection_container,
                CollectionFragment.newInstance("Featured")
            ).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
            mCollectionFragmentRecreated = true;
            return true;
            default:
            return super.onOptionsItemSelected(item);
        }*/
    }

    override fun onItemClick(view: View?, position: Int, drawerItem: IDrawerItem<*>): Boolean {
        /*var intent: Intent? = null

        if (drawerItem.identifier.toInt() == 1) {
            mViewPager.setCurrentItem(0);
        } else if (drawerItem.identifier.toInt() == 2) {
            mViewPager.setCurrentItem(1);
        } else if (drawerItem.identifier.toInt() == 3) {
            mViewPager.setCurrentItem(2);
        } else if (drawerItem.identifier.toInt() == 4) {
            intent = new Intent (MainActivity.this, AutoWallpaperActivity.class);
        } else if (drawerItem.identifier.toInt() == 5) {
            intent = new Intent (MainActivity.this, DonateActivity.class);
        } else if (drawerItem.identifier.toInt() == 6) {
            intent = new Intent (MainActivity.this, SettingsActivity.class);
        } else if (drawerItem.identifier.toInt() == 7) {
            intent = new Intent (MainActivity.this, AboutActivity.class);
        } else if (drawerItem.identifier.toInt() == 100000) {
            intent = new Intent (MainActivity.this, LoginActivity.class);
        } else if (drawerItem.identifier.toInt() == 100001) {
            if (AuthManager.getInstance().isAuthorized()) {
                intent = new Intent (MainActivity.this, UserActivity.class);
                intent.putExtra("username", AuthManager.getInstance().getUsername());
                intent.putExtra(
                    "name",
                    AuthManager.getInstance().getFirstName() + " " + AuthManager.getInstance().getLastName()
                )
            }
        } else if (drawerItem.getIdentifier() == 100002) {
            intent = new Intent (MainActivity.this, EditProfileActivity.class)
        } else if (drawerItem.getIdentifier() == 100003) {
            AuthManager.getInstance().logout()
            updateDrawerItems();
            Toast.makeText(
                getApplicationContext(),
                getString(R.string.main_logout_success),
                Toast.LENGTH_SHORT
            ).show()
        }

        if (intent != null) {
            MainActivity.this.startActivity(intent)
        }
        return false*/
    }

    private fun updateDrawerItems() {
    }

    class PagerAdapter(
        fragmentManager: FragmentManager
    ) : FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

        private val fragmentList = mutableListOf<Fragment>()
        private val fragmentTitleList = mutableListOf<String>()

        override fun getCount(): Int = fragmentList.size

        override fun getItem(position: Int): Fragment = fragmentList[position]

        override fun getPageTitle(position: Int): CharSequence? = fragmentTitleList[position]

        fun addFragment(fragment: Fragment, title: String) {
            fragmentList.add(fragment)
            fragmentTitleList.add(title)
        }
    }

    interface OnHomeFragmentInteractionListener : FragmentInteractionListener

    companion object {
        private const val ERROR_IMPLEMENT_FRAGMENT_INTERACTION_LISTENER =
            "must implement OnHomeFragmentInteractionListener"

        @JvmStatic
        fun newInstance() = HomeFragment()
    }
}
