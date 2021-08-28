import { browser, ExpectedConditions as ec /* , promise */ } from 'protractor';
import { NavBarPage, SignInPage } from '../../../page-objects/jhi-page-objects';

import {
  ArticuloPedidoComponentsPage,
  /* ArticuloPedidoDeleteDialog, */
  ArticuloPedidoUpdatePage,
} from './articulo-pedido.page-object';

const expect = chai.expect;

describe('ArticuloPedido e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let articuloPedidoComponentsPage: ArticuloPedidoComponentsPage;
  let articuloPedidoUpdatePage: ArticuloPedidoUpdatePage;
  /* let articuloPedidoDeleteDialog: ArticuloPedidoDeleteDialog; */

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load ArticuloPedidos', async () => {
    await navBarPage.goToEntity('articulo-pedido');
    articuloPedidoComponentsPage = new ArticuloPedidoComponentsPage();
    await browser.wait(ec.visibilityOf(articuloPedidoComponentsPage.title), 5000);
    expect(await articuloPedidoComponentsPage.getTitle()).to.eq('tiendaApp.productoArticuloPedido.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(articuloPedidoComponentsPage.entities), ec.visibilityOf(articuloPedidoComponentsPage.noResult)),
      1000
    );
  });

  it('should load create ArticuloPedido page', async () => {
    await articuloPedidoComponentsPage.clickOnCreateButton();
    articuloPedidoUpdatePage = new ArticuloPedidoUpdatePage();
    expect(await articuloPedidoUpdatePage.getPageTitle()).to.eq('tiendaApp.productoArticuloPedido.home.createOrEditLabel');
    await articuloPedidoUpdatePage.cancel();
  });

  /* it('should create and save ArticuloPedidos', async () => {
        const nbButtonsBeforeCreate = await articuloPedidoComponentsPage.countDeleteButtons();

        await articuloPedidoComponentsPage.clickOnCreateButton();

        await promise.all([
            articuloPedidoUpdatePage.setCantidadInput('5'),
            articuloPedidoUpdatePage.setTotalInput('5'),
            articuloPedidoUpdatePage.estadoSelectLastOption(),
            articuloPedidoUpdatePage.productoSelectLastOption(),
            articuloPedidoUpdatePage.pedidoSelectLastOption(),
        ]);

        expect(await articuloPedidoUpdatePage.getCantidadInput()).to.eq('5', 'Expected cantidad value to be equals to 5');
        expect(await articuloPedidoUpdatePage.getTotalInput()).to.eq('5', 'Expected total value to be equals to 5');

        await articuloPedidoUpdatePage.save();
        expect(await articuloPedidoUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

        expect(await articuloPedidoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
    }); */

  /* it('should delete last ArticuloPedido', async () => {
        const nbButtonsBeforeDelete = await articuloPedidoComponentsPage.countDeleteButtons();
        await articuloPedidoComponentsPage.clickOnLastDeleteButton();

        articuloPedidoDeleteDialog = new ArticuloPedidoDeleteDialog();
        expect(await articuloPedidoDeleteDialog.getDialogTitle())
            .to.eq('tiendaApp.productoArticuloPedido.delete.question');
        await articuloPedidoDeleteDialog.clickOnConfirmButton();

        expect(await articuloPedidoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    }); */

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
