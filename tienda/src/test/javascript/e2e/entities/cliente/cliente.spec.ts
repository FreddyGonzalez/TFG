import { browser, ExpectedConditions as ec /* , promise */ } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  ClienteComponentsPage,
  /* ClienteDeleteDialog, */
  ClienteUpdatePage,
} from './cliente.page-object';

const expect = chai.expect;

describe('Cliente e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let clienteComponentsPage: ClienteComponentsPage;
  let clienteUpdatePage: ClienteUpdatePage;
  /* let clienteDeleteDialog: ClienteDeleteDialog; */

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Clientes', async () => {
    await navBarPage.goToEntity('cliente');
    clienteComponentsPage = new ClienteComponentsPage();
    await browser.wait(ec.visibilityOf(clienteComponentsPage.title), 5000);
    expect(await clienteComponentsPage.getTitle()).to.eq('tiendaApp.cliente.home.title');
    await browser.wait(ec.or(ec.visibilityOf(clienteComponentsPage.entities), ec.visibilityOf(clienteComponentsPage.noResult)), 1000);
  });

  it('should load create Cliente page', async () => {
    await clienteComponentsPage.clickOnCreateButton();
    clienteUpdatePage = new ClienteUpdatePage();
    expect(await clienteUpdatePage.getPageTitle()).to.eq('tiendaApp.cliente.home.createOrEditLabel');
    await clienteUpdatePage.cancel();
  });

  /* it('should create and save Clientes', async () => {
        const nbButtonsBeforeCreate = await clienteComponentsPage.countDeleteButtons();

        await clienteComponentsPage.clickOnCreateButton();

        await promise.all([
            clienteUpdatePage.setIdClienteInput('idCliente'),
            clienteUpdatePage.setNombreInput('nombre'),
            clienteUpdatePage.setApellidoInput('apellido'),
            clienteUpdatePage.generoSelectLastOption(),
            clienteUpdatePage.setEmailInput('Z@H$G*f.oWYChi'),
            clienteUpdatePage.setTelefonoInput('telefono'),
            clienteUpdatePage.setDireccionInput('direccion'),
            clienteUpdatePage.setCiudadInput('ciudad'),
            clienteUpdatePage.setPaisInput('pais'),
            clienteUpdatePage.userSelectLastOption(),
        ]);

        expect(await clienteUpdatePage.getIdClienteInput()).to.eq('idCliente', 'Expected IdCliente value to be equals to idCliente');
        expect(await clienteUpdatePage.getNombreInput()).to.eq('nombre', 'Expected Nombre value to be equals to nombre');
        expect(await clienteUpdatePage.getApellidoInput()).to.eq('apellido', 'Expected Apellido value to be equals to apellido');
        expect(await clienteUpdatePage.getEmailInput()).to.eq('Z@H$G*f.oWYChi', 'Expected Email value to be equals to Z@H$G*f.oWYChi');
        expect(await clienteUpdatePage.getTelefonoInput()).to.eq('telefono', 'Expected Telefono value to be equals to telefono');
        expect(await clienteUpdatePage.getDireccionInput()).to.eq('direccion', 'Expected Direccion value to be equals to direccion');
        expect(await clienteUpdatePage.getCiudadInput()).to.eq('ciudad', 'Expected Ciudad value to be equals to ciudad');
        expect(await clienteUpdatePage.getPaisInput()).to.eq('pais', 'Expected Pais value to be equals to pais');

        await clienteUpdatePage.save();
        expect(await clienteUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

        expect(await clienteComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
    }); */

  /* it('should delete last Cliente', async () => {
        const nbButtonsBeforeDelete = await clienteComponentsPage.countDeleteButtons();
        await clienteComponentsPage.clickOnLastDeleteButton();

        clienteDeleteDialog = new ClienteDeleteDialog();
        expect(await clienteDeleteDialog.getDialogTitle())
            .to.eq('tiendaApp.cliente.delete.question');
        await clienteDeleteDialog.clickOnConfirmButton();

        expect(await clienteComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    }); */

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
