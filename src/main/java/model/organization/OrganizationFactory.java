package model.organization;

/**
 * The {@linkplain OrganizationFactory} interface defines the APIs for constructing an organization.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
public interface OrganizationFactory {

	/**
	 * Constructs an {@linkplain Organization}.
	 *
	 * @return an {@linkplain Organization}.
	 */
	Organization buildOrganization();

}
