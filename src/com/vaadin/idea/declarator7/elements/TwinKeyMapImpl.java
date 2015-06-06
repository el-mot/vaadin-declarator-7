/*
 * Copyright 2000-2014 Vaadin Ltd.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.vaadin.idea.declarator7.elements;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Provides various helper methods for connectors. Meant for internal use.
 *
 * @author Vaadin Ltd
 */
public class TwinKeyMapImpl<V extends TwinKeyMap.DoubleNamedItem> implements TwinKeyMap<V> {
    private Map<String, V> byJavaName = null;
    private Map<String, V> byDeclName = new HashMap<>();

    public static final TwinKeyMap<?> EMPTY = new EmptyTwinKeyMap();

    public TwinKeyMapImpl() {
    }

    public TwinKeyMapImpl(V... members) {
        this();
        for (V member : members) {
            addMember(member);
        }
    }

    @Override
    public Collection<String> getDeclNames() {
        return byDeclName.keySet();
    }

    private void ensureJavaNames() {
        if (byJavaName == null) {
            byJavaName = new HashMap<>(byDeclName.size());
            for (V v : byDeclName.values()) {
                String javaName = v.getJavaName();
                if (javaName != null) {
                    byJavaName.put(javaName, v);
                }
            }
        }
    }

    @Override
    public Collection<String> getJavaNames() {
        ensureJavaNames();
        return byJavaName.keySet();
    }

    @Override
    public Collection<V> getMembers() {
        return byDeclName.values();
    }

    @Override
    public V getByDeclName(String name) {
        return byDeclName.get(name);
    }

    @Override
    public V getByJavaName(String name) {
        ensureJavaNames();
        return byJavaName.get(name);
    }

    @Override
    public void addMember(V value) {
        byDeclName.put(value.getDeclName(), value);
        byJavaName = null;
    }

    private static class EmptyTwinKeyMap implements TwinKeyMap<TwinKeyMap.DoubleNamedItem> {
        @Override
        public Collection<String> getDeclNames() {
            return Collections.emptyList();
        }

        @Override
        public Collection<String> getJavaNames() {
            return Collections.emptyList();
        }

        @Override
        public Collection<TwinKeyMap.DoubleNamedItem> getMembers() {
            return Collections.emptyList();
        }

        @Override
        public TwinKeyMap.DoubleNamedItem getByDeclName(String name) {
            return null;
        }

        @Override
        public TwinKeyMap.DoubleNamedItem getByJavaName(String name) {
            return null;
        }

        @Override
        public void addMember(DoubleNamedItem value) {
            throw new UnsupportedOperationException();
        }
    }
}
