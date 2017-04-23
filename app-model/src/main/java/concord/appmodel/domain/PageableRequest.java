package concord.appmodel.domain;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Arrays;

/**
 * Created by aboieriu on 4/20/17.
 */
public class PageableRequest implements Pageable{

	private int pageNumber = 1;
	private int pageSize = 100;
	private int offset = 0;

	public PageableRequest(){}

	public PageableRequest(Integer pageNumber, Integer pageSize, Integer offset) {
		if (pageNumber != null) {
			this.pageNumber = pageNumber;
		}
		if (pageSize != null) {
			this.pageSize = pageSize;
		}
		if (offset != null) {
			this.offset = offset;
		}
	}

	@Override
	public int getPageNumber() {
		return pageNumber;
	}

	@Override
	public int getPageSize() {
		return pageSize;
	}

	@Override
	public int getOffset() {
		return offset;
	}

	@Override
	public Sort getSort() {
		return new Sort(Arrays.asList(new Sort.Order(Sort.Direction.DESC, "date")));
	}

	@Override public Pageable next() {
		return null;
	}

	@Override public Pageable previousOrFirst() {
		return null;
	}

	@Override public Pageable first() {
		return null;
	}

	@Override public boolean hasPrevious() {
		return false;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}
}
