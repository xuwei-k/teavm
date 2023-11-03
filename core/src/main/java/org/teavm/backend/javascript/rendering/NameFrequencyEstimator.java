/*
 *  Copyright 2016 Alexey Andreev.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.teavm.backend.javascript.rendering;

import org.teavm.backend.javascript.codegen.NameFrequencyConsumer;
import org.teavm.backend.javascript.codegen.SourceWriterSink;
import org.teavm.model.FieldReference;
import org.teavm.model.MethodDescriptor;
import org.teavm.model.MethodReference;

public class NameFrequencyEstimator implements SourceWriterSink {
    static final MethodReference MONITOR_ENTER_METHOD = new MethodReference(Object.class,
            "monitorEnter", Object.class, void.class);
    static final MethodReference MONITOR_ENTER_SYNC_METHOD = new MethodReference(Object.class,
            "monitorEnterSync", Object.class, void.class);
    static final MethodReference MONITOR_EXIT_METHOD = new MethodReference(Object.class,
            "monitorExit", Object.class, void.class);
    static final MethodReference MONITOR_EXIT_SYNC_METHOD = new MethodReference(Object.class,
            "monitorExitSync", Object.class, void.class);

    private final NameFrequencyConsumer consumer;

    public NameFrequencyEstimator(NameFrequencyConsumer consumer) {
        this.consumer = consumer;
    }

    @Override
    public SourceWriterSink appendClass(String cls) {
        consumer.consume(cls);
        return this;
    }

    @Override
    public SourceWriterSink appendField(FieldReference field) {
        consumer.consume(field);
        return this;
    }

    @Override
    public SourceWriterSink appendStaticField(FieldReference field) {
        consumer.consumeStatic(field);
        return this;
    }

    @Override
    public SourceWriterSink appendMethod(MethodDescriptor method) {
        consumer.consume(method);
        return this;
    }

    @Override
    public SourceWriterSink appendMethodBody(MethodReference method) {
        consumer.consume(method);
        return this;
    }

    @Override
    public SourceWriterSink appendFunction(String name) {
        consumer.consumeFunction(name);
        return this;
    }

    @Override
    public SourceWriterSink appendGlobal(String name) {
        consumer.consumeGlobal(name);
        return this;
    }

    @Override
    public SourceWriterSink appendInit(MethodReference method) {
        consumer.consumeInit(method);
        return this;
    }

    @Override
    public SourceWriterSink appendClassInit(String className) {
        consumer.consumeClassInit(className);
        return this;
    }
}
