import org.rocksdb.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

class RocksDBSample {
    private static final String dbPath = "/home/liyuankun/learn/leveldb/db";

    static {
        RocksDB.loadLibrary();
    }

    public void readFromDB() throws RocksDBException {
       RocksDB rocksDB;
       DBOptions dbOptions = new DBOptions();
       final List<ColumnFamilyDescriptor> cfDescs = new ArrayList<>();
       String path = dbPath + "/" + OptionsUtil.getLatestOptionsFileName(dbPath, Env.getDefault());
       OptionsUtil.loadOptionsFromFile(path,Env.getDefault(),dbOptions,cfDescs,false);

       final List<ColumnFamilyHandle> columnFamilyHandles = new ArrayList<>();
       rocksDB = RocksDB.open(dbOptions,dbPath,cfDescs,columnFamilyHandles);

        RocksIterator iter = rocksDB.newIterator();
        for (iter.seekToFirst(); iter.isValid(); iter.next()) {
            System.out.println("iter key:" + new String(iter.key()) + ", iter value:" + new String(iter.value()));
        }

    }

    public void getNewDB() throws RocksDBException,IOException {
        RocksDB rocksDB;
        Options options = new Options();
        options.setCreateIfMissing(true);

        if (!Files.isSymbolicLink(Paths.get(dbPath)))
            Files.createDirectories(Paths.get(dbPath));
        rocksDB = RocksDB.open(options, dbPath);

        byte[] key = "Hello".getBytes();
        byte[] value = "World".getBytes();
        rocksDB.put(key, value);

        byte[] getValue = rocksDB.get(key);
        System.out.println(new String(getValue));

        rocksDB.close();
    }

    public static void main(String[] args) throws Exception{
        RocksDBSample test = new RocksDBSample();
//        test.getNewDB();

        test.readFromDB();
    }
}