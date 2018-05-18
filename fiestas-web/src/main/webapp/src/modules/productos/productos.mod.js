/**
 * @ngdoc overview
 * @name productos.module:productoModule
 * @description
 * Definición del módulo de Angular de Producto. El módulo encapsula todos los 
 * controladores y los templates HTML que estén relacionados con la Producto 
 * directamente. En la configuración del módulo se injecta la dependencia de 
 * ui.router que es la que se utiliza para la configuración de las URLs bajo las
 * cuales se accede al módulo. Por ejemplo, para mostrar las editoriales en la 
 * URL: 'localhost:8080/productos/list' es necesario configurar el router por 
 * medio del stateProvider que informa a AngularJS de la relación entre la URL, 
 * un estado definido (estado de mostrar productos), el controlador y la vista 
 * correspondiente. Los estados definidos en este modulo son:
 * ```
 * | ESTADO           | URL                        | VISTAS                 |
 * |------------------|----------------------------|------------------------|
 * | productos            | /productos                     | mainView:              |
 * |                  |                            | productos.html           |
 * |                  |                            |                        |
 * | productosList        | /list                      | listView:              |
 * |                  |                            | productos.list.html      |
 * |                  |                            |                        |
 * | productoDetail       | /{productoId:int}/detail       | listView:              |
 * |                  |                            | productos.list.html      |
 * |                  |                            | detailView:            |
 * |                  |                            | productos.detail.html    |
 * | productosCreate      | /create                    | detailView: (/new)     |
 * |                  |                            | /productos.new.html      |
 * | productoUpdate       | /update/{productoId:int}       | detailView: (/new)     |
 * |                  |                            | /productos.new.html      |
 * | productoDelete       | /delete/{productoId:int}       | detailView: (/delete)  |
 * |                  |                            | /producto.delete.html    |
 * |------------------|----------------------------|------------------------|
 *```
 */
(function (ng) {
    var mod = ng.module("productosModule", ['ui.router']);
    mod.constant("productosContext", "productos");
    mod.constant("proveedoresContext", "api/proveedores");
    mod.config(['$stateProvider', '$urlRouterProvider', function ($stateProvider, $urlRouterProvider) {
            

            var basePath = 'src/modules/productos/';

            $urlRouterProvider.otherwise("/productosList");

            $stateProvider.state('productos', {
                url: '/productos',
                abstract: true,
                parent: 'proveedorDetail',
                views: {
                    'childrenView': {
                        templateUrl: basePath + 'productos.html',
                        controller: 'productosCtrl',
                        controllerAs: 'ctrl'
                    }
                },
                data: {
                    requireLogin: false,
                   roles: ['Admin','Proveedor','Cliente']
                }
            }).state('productosList', {
                url: '/list',
                parent: 'productos',
                views: {
                    'listView': {
                        templateUrl: basePath + 'productos.list.html'
                    }
                }
            }).state('productosDetail', {
                url: '/{productoId:int}/detail',
                parent: 'productos',
                param:{productoId : null},
                views: {
                    'listView': {
                        templateUrl: basePath + 'productos.list.html'
                    },
                    'detailView': {
                        templateUrl: basePath + 'productos.detail.html',
                        controller: 'productosDetailCtrl',
                        controllerAs: 'ctrl'
                    }
                }

            }).state('productosCreate', {
                url: '/create',
                parent: 'productos',
                params:{productoId : null, proveedorId : null},
                views: {
                    'detailView': {
                        templateUrl: basePath + '/new/productos.new.html',
                        controller: 'productosNewCtrl'
                    }
                }
            }).state('productoUpdate', {
                url: '/update/{productoId:int}',
                parent: 'productos',
                param: {
                    productoId: null, proveedorId : null
                },
                views: {
                    'detailView': {
                        templateUrl: basePath + '/new/productos.new.html',
                        controller: 'productoUpdateCtrl'
                    }
                },
                data: {
                    requireLogin: true,
                   roles: ['Admin','Proveedor']
                }
            }).state('productoDelete', {
                url: '/delete/{productoId:int}',
                parent: 'productos',
                param: {
                    productoId: null
                },
                views: {
                    'detailView': {
                        templateUrl: basePath + '/delete/productos.delete.html',
                        controller: 'productoDeleteCtrl'
                    }
                },
                data: {
                    requireLogin: true,
                   roles: ['Admin','Proveedor']
                }
            }).state('productosAll', {
                url: '/{productoId:int}/detailAll',
                parent: 'productos',
                params:[
                    {productoId: null}, {proveedorId: null}
                ],  
                views: {
                    'listView': {
                        templateUrl: basePath + 'productos.listAll.html'
                    },
                    'mainView': {
                        templateUrl: basePath + 'productos.detail.html',
                        controller: 'productosAllCtrl'
                    }
                },
                data: {
                    requireLogin: true,
                   roles: ['Admin','Proveedor']
                }
            });
        }

    ]);
})(window.angular);

