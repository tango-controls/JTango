package fr.soleil.TangoSnapshoting.SnapshotingApi.persistence;

import fr.soleil.actiongroup.collectiveaction.onattributes.plugin.persistance.PersistenceManager;

public interface SnapshotPersistenceManager extends PersistenceManager 
{
    public String getResourceName () ;
}
