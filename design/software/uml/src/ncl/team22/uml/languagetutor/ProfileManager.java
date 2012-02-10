package ncl.team22.uml.languagetutor;

import java.util.ArrayList;
import java.util.Collection;

public class ProfileManager
{
		
		public ArrayList<Profile> listProfiles(){
			return null;
		}
		
		public Profile getProfile(int id){
			return null;
		}

		/**
		 * @uml.property  name="profile1"
		 * @uml.associationEnd  multiplicity="(0 -1)" inverse="profileManager:ncl.team22.uml.languagetutor.Profile"
		 */
		private Collection<Profile> profile1;

		/**
		 * Getter of the property <tt>profile1</tt>
		 * @return  Returns the profile1.
		 * @uml.property  name="profile1"
		 */
		public Collection<Profile> getProfile1()
		{
			return profile1;
		}

		/**
		 * Setter of the property <tt>profile1</tt>
		 * @param profile1  The profile1 to set.
		 * @uml.property  name="profile1"
		 */
		public void setProfile1(Collection<Profile> profile1)
		{
			this.profile1 = profile1;
		}

			
		/**
			 */
		public boolean createNewProfile(String profileName, String password, String secret_q, String secret_a){
				return false;	
		}

}
