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

    RocksDB rocksDB;

    public void testDefaultColumnFamily() throws RocksDBException, IOException{
        Options options = new Options();
        options.setCreateIfMissing(true);

        if(!Files.isSymbolicLink(Paths.get(dbPath)))
            Files.createDirectories(Paths.get(dbPath));
        rocksDB = RocksDB.open(options,dbPath);

        RocksIterator iterator = rocksDB.newIterator();
        for (iterator.seekToFirst();iterator.isValid(); iterator.next() ){
         System.out.println("iterator key: "+ new String(iterator.key()) + ", iterator value: " + new String(iterator.value()));
        }

    }

    public static void main(String[] args) throws Exception{
        RocksDBSample test = new RocksDBSample();
        test.testDefaultColumnFamily();
    }
}