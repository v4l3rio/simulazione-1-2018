package e1;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;


public class SplitIteratorFactoryImpl implements SplitIteratorFactory {

	@Override
	public SplitIterator<Integer> fromRange(int start, int stop) {
		List<Integer> list = new ArrayList<>();
		for (int i = start; i <= stop; i++) {
			list.add(i);
		}
		return fromList(list);

	}

	@Override
	public SplitIterator<Integer> fromRangeNoSplit(int start, int stop) {
		return new SplitIterator<Integer>() {

			int current = start;

			@Override
			public SplitIterator<Integer> split() {
				throw new UnsupportedOperationException();
			}

			@Override
			public Optional<Integer> next() {
				if (current <= stop)
					return Optional.of(current++);
				return Optional.empty();

			}
		};

	}

	@Override
	public <X> SplitIterator<X> fromList(List<X> list) {
		return new SplitIterator<X>() {

			List<X> copyOfList = new ArrayList<X>(list);

			int current = 0;

			@Override
			public Optional<X> next() {
				if (current < copyOfList.size()) {

					return Optional.of(copyOfList.get(current++));
				}
				return Optional.empty();
			}

			@Override
			public SplitIterator<X> split() {
				int offset = (copyOfList.size() + current) / 2;
				int tmp = current;
				current = offset - current + 1;
				return fromList(copyOfList.subList(tmp, offset));
			}
		};
	}

	@Override
	public <X> SplitIterator<X> fromListNoSplit(List<X> list) {
		return new SplitIterator<X>() {

			List<X> copyOfList = new ArrayList<X>(list);

			int current = 0;

			@Override
			public Optional<X> next() {
				if (current < copyOfList.size()) {

					return Optional.of(copyOfList.get(current++));
				}
				return Optional.empty();
			}

			@Override
			public SplitIterator<X> split() {
				throw new UnsupportedOperationException();
			}
		};
	}

	@Override
	public <X> SplitIterator<X> excludeFirst(SplitIterator<X> si) {
		return new SplitIterator<X>() {

			int count = 0;

			@Override
			public Optional<X> next() {
				if (count == 0) {
					si.next();
					count++;
				}
				return si.next();
			}

			@Override
			public SplitIterator<X> split() {
				return si.split();
			}

		};
	}

	@Override
	public <X, Y> SplitIterator<Y> map(SplitIterator<X> si, Function<X, Y> mapper) {
		return new SplitIterator<Y>() {
			

			@Override
			public Optional<Y> next() {
			return si.next().map(mapper);
				
			}

			@Override
			public SplitIterator<Y> split() {
				return map(si.split(),mapper);
			}
		};
		
	}

}
