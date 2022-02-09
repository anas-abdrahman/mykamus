package com.mynasmah.mykamus.utils.lucene;


import org.apache.lucene.search.spell.Dictionary;
import org.apache.lucene.search.suggest.InputIterator;
import org.apache.lucene.util.BytesRef;
import org.apache.lucene.util.BytesRefBuilder;
import org.apache.lucene.util.BytesRefIterator;
import org.apache.lucene.util.IOUtils;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class DictionaryFile implements Dictionary {

    private BufferedReader in;

    public DictionaryFile(InputStreamReader reader) {
        this.in = new BufferedReader(reader);
    }

    public InputIterator getEntryIterator() {
        return new InputIterator.InputIteratorWrapper(new FileIterator());
    }

    final class FileIterator implements BytesRefIterator {

        private boolean done = false;
        private final BytesRefBuilder spare = new BytesRefBuilder();

        FileIterator() { }

        public BytesRef next() throws IOException {

            if (done) {
                return null;

            } else {

                boolean success = false;

                BytesRef result;

                try {

                    String line;

                    if ((line = in.readLine()) != null) {

                        spare.copyChars(line);
                        result = spare.get();

                    } else {

                        done = true;
                        IOUtils.close(in);
                        result = null;

                    }

                    success = true;

                } finally {

                    if (!success) {
                        IOUtils.closeWhileHandlingException(in);
                    }

                }

                return result;
            }
        }
    }
}

