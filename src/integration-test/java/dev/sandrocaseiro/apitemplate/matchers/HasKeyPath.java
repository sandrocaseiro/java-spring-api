package dev.sandrocaseiro.apitemplate.matchers;

import org.hamcrest.Condition;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

import java.util.*;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasKey;

public class HasKeyPath<K, V> extends TypeSafeDiagnosingMatcher<Map<? extends K, ? extends V>> {
    private final String keyName;
    private final Matcher<Map<? extends String, ?>> nextKeyMatcher;

    public HasKeyPath(String keyName, Matcher<Map<? extends String, ?>> nextKeyMatcher) {
        this.keyName = keyName;
        this.nextKeyMatcher = nextKeyMatcher;
    }

    @Override
    protected boolean matchesSafely(Map<? extends K, ? extends V> map, Description mismatch) {
        return keyOn(map, mismatch)
            .matching(convert(nextKeyMatcher));
    }

    private Condition<Map> keyOn(Map<? extends K, ? extends V> map, Description mismatch) {
        Iterator var2 = map.entrySet().iterator();

        Map.Entry entry;
        do {
            if (!var2.hasNext()) {
                return Condition.notMatched();
            }

            entry = (Map.Entry)var2.next();
        } while(!equalTo(this.keyName).matches(entry.getKey()));

        return Condition.matched((Map)entry.getValue(), mismatch);
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("map containing [").appendValue(this.keyName).appendText("->").appendDescriptionOf(this.nextKeyMatcher).appendText("]");
    }

    public Matcher<Map> convert(Matcher<?> matcher) {
        return (Matcher<Map>) matcher;
    }

    public static <T> Matcher<T> hasKeyAtPath(String path) {
        List<String> keys = Arrays.asList(path.split("\\."));
        ListIterator<String> iterator = keys.listIterator(keys.size());

        Object ret = null;
        for(; iterator.hasPrevious(); ret = ret == null
            ?  hasKey(iterator.previous())
            :  new HasKeyPath(iterator.previous(), (Matcher)ret)) {
        }

        return (Matcher)ret;
    }
}
