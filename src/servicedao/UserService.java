package servicedao;

import java.util.List;

public interface UserService {

	void addUser(String lecCode, String lecName);
	
	List<String> getUser();
}
