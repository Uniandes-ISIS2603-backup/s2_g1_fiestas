(function (ng) {
    var mod = ng.module("productosModule");
    mod.constant("productosContext", "productos");
    mod.constant("proveedoresContext", "api/proveedores");
    mod.controller('productosCtrl', ['$scope', '$http', 'productosContext','proveedoresContext', '$state', '$rootScope',
        function ($scope, $http, productosContext, proveedoresContext, $state, $rootScope) {
             /**
             * @ngdoc controller
             * @name productos.controller:productoDetailCtrl
             * @description
             * Definición de un controlador auxiliar del módulo Productos. 
             * Se crea el controlador con el cual se manejan las vistas de detalle
             * del módulo.
             * @param {Object} $scope Referencia injectada al Scope definida para este
             * controlador, el scope es el objeto que contiene las variables o 
             * funciones que se definen en este controlador y que son utilizadas 
             * desde el HTML.
             * @param {Object} $http Objeto injectado para la manejar consultas HTTP
             * @param {Object} $state Dependencia injectada en la que se recibe el 
             * estado actual de la navegación definida en el módulo.
             * @param {Object} productoContext Constante injectada que contiene la ruta
             * donde se encuentra el API de Productos en el Backend.
             * *@param {Object} clientesContext Constante injectada que contiene la ruta
             * donde se encuentra el API de Proveedors en el Backend.
             */
            $rootScope.proveedorId = $state.params.proveedorId;
            $scope.proveedorId = $state.params.proveedorId;
            $rootScope.productoId = $state.params.productoId;
            if($state.params.proveedorId !== null && $state.params.proveedorId !== undefined )
            {
                /**
                 * @ngdoc function
                 * @name getPagoID
                 * @methodOf productos.controller:productoDetailCtrl
                 * @description
                 * Esta función utiliza el protocolo HTTP para obtener el recurso 
                 * donde se encuentra el proveedor por ID en formato JSON.
                 * @param {String} URL Dirección donde se encuentra el recurso
                 * del proveedor o API donde se puede consultar.
                 */
            $http.get(proveedoresContext + '/' + $state.params.proveedorId + '/' + productosContext).then(function (response) {
                $rootScope.productosRecords = response.data;
            });
        }
        }
    ]);
}
)(window.angular);  