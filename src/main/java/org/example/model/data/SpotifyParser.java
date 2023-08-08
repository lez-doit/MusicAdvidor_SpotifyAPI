package org.example.model.data;

import java.util.List;

@FunctionalInterface
public interface SpotifyParser {
    List<? extends Parseable> parse(String body);
}
