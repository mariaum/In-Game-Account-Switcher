package the_fireplace.ias.gui;

import com.github.mrebhan.ingameaccountswitcher.tools.alt.AccountData;
import com.github.mrebhan.ingameaccountswitcher.tools.alt.AltDatabase;
import the_fireplace.ias.account.ExtendedAccountData;
import the_fireplace.ias.enums.EnumBool;
import the_fireplace.ias.tools.JavaTools;
import the_fireplace.iasencrypt.EncryptionTools;
/**
 * The GUI where the alt is added
 * @author The_Fireplace
 * @author evilmidget38
 */
class GuiEditAccount extends AbstractAccountGui {
	private final ExtendedAccountData data;
	private final int selectedIndex;

	private String currentUsername;
	private String currentPassword;

	public GuiEditAccount(int index){
		super("ias.editaccount");
		this.selectedIndex=index;
		AccountData data = AltDatabase.getInstance().getAlts().get(index);

		if(data instanceof ExtendedAccountData){
			this.data = (ExtendedAccountData) data;
		}else{
			this.data = new ExtendedAccountData(data.user, data.pass, data.alias, 0, JavaTools.getJavaCompat().getDate(), EnumBool.UNKNOWN);
		}

		this.currentUsername = EncryptionTools.decode(data.user);
		this.currentPassword = EncryptionTools.decode(data.pass);
	}

	@Override
	public void initGui() {
		super.initGui();
		setUsername(this.currentUsername);
		setPassword(this.currentPassword);
	}

	@Override
	public void complete()
	{
		AltDatabase.getInstance().getAlts().set(selectedIndex, new ExtendedAccountData(getUsername(), getPassword(), getUsername(), data.useCount, data.lastused, data.premium));
	}

	@Override
	public boolean canComplete() {
		return !this.password.getText().equals(this.currentPassword) || !this.username.getText().equals(this.currentUsername);
	}

}
