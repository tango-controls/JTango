package fr.esrf.TangoApi.Group;

import org.junit.Assert;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.CopyOnWriteArrayList;

public abstract class CollectionTestHelper<T extends CopyOnWriteArrayList<S>, S extends GroupReply> {

    private final Class<T> collectionClass;
    protected T collection;

    public CollectionTestHelper(Class<T> collectionClass) {
        this.collectionClass = collectionClass;
    }

    protected void setup(String... args) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        collection = collectionClass.getConstructor().newInstance();
        if (args.length % 2 != 0) {
            System.out.println("The args length must be even number");
            return;
        }

        for (int i = 0; i < args.length; i += 2) {
            collection.add(create(args[i], args[i + 1]));
        }
    }

    protected void doTestRead(int tabLength) {
        for (int i = 0; i < tabLength; i++) {
            S reply = collection.get(i);
            Assert.assertNotNull(reply);
        }
    }

    protected S doTestAdd(String devName, String objName) {
        collection.add(create(devName, objName));
        S reply = collection.get(collection.size() - 1);

        Assert.assertEquals(devName, reply.dev_name);
        Assert.assertEquals(objName, reply.obj_name);
        Assert.assertTrue(reply.has_failed);

        return reply;
    }

    protected abstract S create(String devName, String objName);
}
