import { element, by, ElementFinder } from 'protractor';

export class ComentarioComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-comentario div table .btn-danger'));
  title = element.all(by.css('jhi-comentario div h2#page-heading span')).first();
  noResult = element(by.id('no-result'));
  entities = element(by.id('entities'));

  async clickOnCreateButton(): Promise<void> {
    await this.createButton.click();
  }

  async clickOnLastDeleteButton(): Promise<void> {
    await this.deleteButtons.last().click();
  }

  async countDeleteButtons(): Promise<number> {
    return this.deleteButtons.count();
  }

  async getTitle(): Promise<string> {
    return this.title.getAttribute('jhiTranslate');
  }
}

export class ComentarioUpdatePage {
  pageTitle = element(by.id('jhi-comentario-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  idComentarioInput = element(by.id('field_idComentario'));
  descripcionInput = element(by.id('field_descripcion'));
  productoIdInput = element(by.id('field_productoId'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setIdComentarioInput(idComentario: string): Promise<void> {
    await this.idComentarioInput.sendKeys(idComentario);
  }

  async getIdComentarioInput(): Promise<string> {
    return await this.idComentarioInput.getAttribute('value');
  }

  async setDescripcionInput(descripcion: string): Promise<void> {
    await this.descripcionInput.sendKeys(descripcion);
  }

  async getDescripcionInput(): Promise<string> {
    return await this.descripcionInput.getAttribute('value');
  }

  async setProductoIdInput(productoId: string): Promise<void> {
    await this.productoIdInput.sendKeys(productoId);
  }

  async getProductoIdInput(): Promise<string> {
    return await this.productoIdInput.getAttribute('value');
  }

  async save(): Promise<void> {
    await this.saveButton.click();
  }

  async cancel(): Promise<void> {
    await this.cancelButton.click();
  }

  getSaveButton(): ElementFinder {
    return this.saveButton;
  }
}

export class ComentarioDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-comentario-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-comentario'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
