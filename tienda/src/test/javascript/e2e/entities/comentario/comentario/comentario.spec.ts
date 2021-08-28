import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../../page-objects/jhi-page-objects';

import { ComentarioComponentsPage, ComentarioDeleteDialog, ComentarioUpdatePage } from './comentario.page-object';

const expect = chai.expect;

describe('Comentario e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let comentarioComponentsPage: ComentarioComponentsPage;
  let comentarioUpdatePage: ComentarioUpdatePage;
  let comentarioDeleteDialog: ComentarioDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Comentarios', async () => {
    await navBarPage.goToEntity('comentario');
    comentarioComponentsPage = new ComentarioComponentsPage();
    await browser.wait(ec.visibilityOf(comentarioComponentsPage.title), 5000);
    expect(await comentarioComponentsPage.getTitle()).to.eq('tiendaApp.comentarioComentario.home.title');
    await browser.wait(ec.or(ec.visibilityOf(comentarioComponentsPage.entities), ec.visibilityOf(comentarioComponentsPage.noResult)), 1000);
  });

  it('should load create Comentario page', async () => {
    await comentarioComponentsPage.clickOnCreateButton();
    comentarioUpdatePage = new ComentarioUpdatePage();
    expect(await comentarioUpdatePage.getPageTitle()).to.eq('tiendaApp.comentarioComentario.home.createOrEditLabel');
    await comentarioUpdatePage.cancel();
  });

  it('should create and save Comentarios', async () => {
    const nbButtonsBeforeCreate = await comentarioComponentsPage.countDeleteButtons();

    await comentarioComponentsPage.clickOnCreateButton();

    await promise.all([
      comentarioUpdatePage.setIdComentarioInput('idComentario'),
      comentarioUpdatePage.setDescripcionInput('descripcion'),
      comentarioUpdatePage.setProductoIdInput('5'),
    ]);

    expect(await comentarioUpdatePage.getIdComentarioInput()).to.eq(
      'idComentario',
      'Expected IdComentario value to be equals to idComentario'
    );
    expect(await comentarioUpdatePage.getDescripcionInput()).to.eq('descripcion', 'Expected Descripcion value to be equals to descripcion');
    expect(await comentarioUpdatePage.getProductoIdInput()).to.eq('5', 'Expected productoId value to be equals to 5');

    await comentarioUpdatePage.save();
    expect(await comentarioUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await comentarioComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Comentario', async () => {
    const nbButtonsBeforeDelete = await comentarioComponentsPage.countDeleteButtons();
    await comentarioComponentsPage.clickOnLastDeleteButton();

    comentarioDeleteDialog = new ComentarioDeleteDialog();
    expect(await comentarioDeleteDialog.getDialogTitle()).to.eq('tiendaApp.comentarioComentario.delete.question');
    await comentarioDeleteDialog.clickOnConfirmButton();

    expect(await comentarioComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
