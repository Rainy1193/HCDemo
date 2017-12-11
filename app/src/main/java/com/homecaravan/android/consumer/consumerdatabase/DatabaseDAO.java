package com.homecaravan.android.consumer.consumerdatabase;

import android.util.Log;

import com.homecaravan.android.consumer.consumerdata.ConsumerAgentData;
import com.homecaravan.android.consumer.consumerdata.ConsumerListingFullStatus;
import com.homecaravan.android.consumer.consumerdata.ConsumerSingletonListing;
import com.homecaravan.android.consumer.listener.IGetSavedSearchListener;
import com.homecaravan.android.consumer.listener.ISavedSearchListener;
import com.homecaravan.android.consumer.listener.IUpdateSavedSearchSuccess;
import com.homecaravan.android.consumer.model.CollaboratorDB;
import com.homecaravan.android.consumer.model.ConsumerAgent;
import com.homecaravan.android.consumer.model.ConsumerAgentInvite;
import com.homecaravan.android.consumer.model.CurrentSavedSearch;
import com.homecaravan.android.consumer.model.ListingDB;
import com.homecaravan.android.consumer.model.SavedSearch;
import com.homecaravan.android.consumer.utils.Utils;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

public class DatabaseDAO {
    private static final String TAG = DatabaseDAO.class.getSimpleName();
    private Realm mRealm;

    public DatabaseDAO(Realm realm) {
        this.mRealm = realm;
    }

    public Realm getRealm() {
        return mRealm;
    }

    public void addSavedSearchDefault() {
        if (!checkDataSavedSearch()) {
            mRealm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    insertSavedSearch(realm, "Costa Mesa Search", "3+", "2+", "250+", "0", 0, 4);
                    insertSavedSearch(realm, "HB Condos", "4+", "3+", "350+", "0", 1, 5);
                    insertSavedSearch(realm, "HB Homes", "5+", "4+", "450+", "0", 2, 6);
                }
            });
        }
    }

    public void addNewSavedSearch(final String name, final String bath, final String bed, final String living, final String newListing,
                                  final int numberListing, final ISavedSearchListener listener, final ArrayList<String> listingId, final ArrayList<ConsumerAgentInvite> agentInvites) {
        mRealm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                SavedSearch savedSearch = realm.createObject(SavedSearch.class);
                savedSearch.setIdSearch(Utils.renderIdSavedSearch());
                savedSearch.setNameSearch(name);
                savedSearch.setBath(bath);
                savedSearch.setBed(bed);
                savedSearch.setLiving(living);
                savedSearch.setNewListing(newListing);
                savedSearch.setFoundListing(String.valueOf(numberListing));
                addNewAgent(realm, savedSearch, agentInvites);
                addNewListing(realm, savedSearch, listingId);

            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                CurrentSavedSearch.getInstance().setSavedSearch(mRealm.where(SavedSearch.class).findAll().last());
                listener.savedSearchSuccess();
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                Log.e(TAG, error.toString());
            }
        });
    }

    public void addNewAgent(Realm realm, SavedSearch savedSearch, ArrayList<ConsumerAgentInvite> agentInvites) {
        RealmList<CollaboratorDB> collaboratorDBs = new RealmList<>();
        for (int i = 0; i < agentInvites.size(); i++) {
            CollaboratorDB collaboratorDB = realm.createObject(CollaboratorDB.class);
            ConsumerAgent agent = agentInvites.get(i).getAgent();
            collaboratorDB.setId(agent.getId());
            collaboratorDB.setFirstName(agent.getFirstName());
            collaboratorDB.setLastName(agent.getLastName());
            collaboratorDB.setPhone(agent.getPhone());
            collaboratorDB.setEmail(agent.getEmail());
            collaboratorDB.setPhoto(agent.getPhoto());
            collaboratorDB.setRoleId(agent.getRoleId());
            collaboratorDBs.add(collaboratorDB);
        }

        savedSearch.setCollaborators(collaboratorDBs);
    }

    public void addNewListing(Realm realm, SavedSearch savedSearch, ArrayList<String> listingId) {
        RealmList<ListingDB> listingDBs = new RealmList<>();
        for (int i = 0; i < listingId.size(); i++) {
            ListingDB listingDB = realm.createObject(ListingDB.class);
            listingDB.setId(listingId.get(i));
            listingDBs.add(listingDB);
        }
        savedSearch.setListings(listingDBs);
    }

    public void insertSavedSearch(Realm realm, String name, String bath, String bed, String living, String newListing, int index, int numberListing) {
        SavedSearch savedSearch = realm.createObject(SavedSearch.class);
        savedSearch.setIdSearch(Utils.renderIdSavedSearch());
        savedSearch.setNameSearch(name);
        savedSearch.setBath(bath);
        savedSearch.setBed(bed);
        savedSearch.setLiving(living);
        savedSearch.setNewListing(newListing);
        savedSearch.setFoundListing(String.valueOf(numberListing));
        addAgentDefault(realm, savedSearch, index);
        addListingDefault(realm, savedSearch, numberListing);
    }

    public void addListingDefault(Realm realm, SavedSearch savedSearch, int numberListing) {

        // issue maybe begin here, note ...

        RealmList<ListingDB> listingDBs = new RealmList<>();
        ArrayList<ConsumerListingFullStatus> listingSavedSearches = ConsumerSingletonListing.getInstance().arrListing;
        for (int i = 0; i < numberListing; i++) {
            ListingDB listingDB = realm.createObject(ListingDB.class);
            listingDB.setId(listingSavedSearches.get(i).getListing().getId());
            listingDBs.add(listingDB);
        }
        savedSearch.setListings(listingDBs);
    }


    public void addAgentDefault(Realm realm, SavedSearch savedSearch, int index) {
        RealmList<CollaboratorDB> collaboratorDBs = new RealmList<>();
        CollaboratorDB collaboratorDB = realm.createObject(CollaboratorDB.class);
        ConsumerAgent agent = ConsumerAgentData.mAgentData.mData.getAgents().get(index);
        collaboratorDB.setId(agent.getId());
        collaboratorDB.setFirstName(agent.getFirstName());
        collaboratorDB.setLastName(agent.getLastName());
        collaboratorDB.setPhone(agent.getPhone());
        collaboratorDB.setEmail(agent.getEmail());
        collaboratorDB.setPhoto(agent.getPhoto());
        collaboratorDB.setRoleId(agent.getRoleId());
        collaboratorDBs.add(collaboratorDB);
        savedSearch.setCollaborators(collaboratorDBs);
    }

    public boolean checkDataSavedSearch() {
        RealmResults<SavedSearch> savedSearches = mRealm.where(SavedSearch.class).findAll();
        return savedSearches.size() != 0;
    }

    public void getAllSavedSearchListener(final IGetSavedSearchListener listener) {

        ArrayList<SavedSearch> searches = new ArrayList<>();
        RealmResults<SavedSearch> savedSearches = mRealm.where(SavedSearch.class).findAll();
        for (int i = 0; i < savedSearches.size(); i++) {
            searches.add(0, savedSearches.get(i));
        }
        listener.getSuccess(searches);
    }

    public ArrayList<SavedSearch> getAllSavedSearch() {
        ArrayList<SavedSearch> searches = new ArrayList<>();
        RealmResults<SavedSearch> savedSearches = mRealm.where(SavedSearch.class).findAll();
        for (int i = 0; i < savedSearches.size(); i++) {
            searches.add(0, savedSearches.get(i));
        }
        return searches;
    }

    public void updateNameSearch(final String id, final String newName, final IUpdateSavedSearchSuccess listener) {
        mRealm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                SavedSearch search = realm.where(SavedSearch.class).equalTo("idSearch", id).findFirst();
                search.setNameSearch(newName);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                listener.updateSuccess("name");
                Log.e(TAG, "onSuccess");
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                Log.e(TAG, error.toString());
            }
        });
    }

    public void updateListAgent(final String id, final ArrayList<ConsumerAgentInvite> agentInvites, final IUpdateSavedSearchSuccess listener) {
        mRealm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                SavedSearch search = realm.where(SavedSearch.class).equalTo("idSearch", id).findFirst();
                RealmList<CollaboratorDB> collaboratorDBs = new RealmList<>();
                for (int i = 0; i < agentInvites.size(); i++) {
                    if (agentInvites.get(i).isPick()) {
                        ConsumerAgent agent = agentInvites.get(i).getAgent();
                        CollaboratorDB collaboratorDB = realm.createObject(CollaboratorDB.class);
                        collaboratorDB.setId(agent.getId());
                        collaboratorDB.setFirstName(agent.getFirstName());
                        collaboratorDB.setLastName(agent.getLastName());
                        collaboratorDB.setPhone(agent.getPhone());
                        collaboratorDB.setEmail(agent.getEmail());
                        collaboratorDB.setPhoto(agent.getPhoto());
                        collaboratorDB.setRoleId(agent.getRoleId());
                        collaboratorDBs.add(collaboratorDB);
                    }
                }
                search.setCollaborators(collaboratorDBs);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                listener.updateSuccess("team");
                Log.e(TAG, "onSuccess");
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                Log.e(TAG, error.toString());
            }
        });
    }

    public SavedSearch getSavedSearch(String id) {
        return mRealm.where(SavedSearch.class).equalTo("idSearch", id).findFirst();
    }

}
