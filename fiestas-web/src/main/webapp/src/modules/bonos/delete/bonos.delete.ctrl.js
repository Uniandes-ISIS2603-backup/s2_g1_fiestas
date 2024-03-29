/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

(function(ng){
    var mod =  ng.module ("bonosModule");
    mod.constant("bonosContext", "api/bonos");
    mod.controller('bonosDeleteCtrl', ['$scope', '$http', 'bonosContext', '$state',
        /**
         * @ngdoc controller
         * @name clientes.controller:clienteDeleteCtrl
         * @description
         * Definición del controlador auxiliar para eliminar Autores. 
         * @param {Object} $scope Referencia injectada al Scope definida para este
         * controlador, el scope es el objeto que contiene las variables o 
         * funciones que se definen en este controlador y que son utilizadas 
         * desde el HTML.
         * @param {Object} $http Objeto injectado para la manejar consultas HTTP
         * @param {Object} clientesContext Constante injectada que contiene la ruta
         * donde se encuentra el API de Autores en el Backend.
         * @param {Object}          
         * * @param {Object} clientesContext Constante injectada que contiene la ruta
         $state Dependencia injectada en la que se recibe el 
         * estado actual de la navegación definida en el módulo.
         */
        function ($scope, $http, bonosContext, $state) {
            var idBonos = $state.params.bonosId;
            /**
             * @ngdoc function
             * @name deleteCliente
             * @methodOf clientes.controller:clienteDeleteCtrl
             * @description
             * Esta función utiliza el protocolo HTTP para eliminar el autor.
             * @param {String} id El ID del autor a eliminar.
             */        
            $scope.deleteBonos = function () {
                $http.delete(bonosContext + '/' + idBonos, {}).then(function (response) {
                    $state.go('bonosList', {bonosId: response.data.id}, {reload: true});
                });
            };
        }
    ]);
}
)(window.angular);
