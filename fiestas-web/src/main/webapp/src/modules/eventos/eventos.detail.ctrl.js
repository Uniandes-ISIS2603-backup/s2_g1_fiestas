(function (ng) {
    var mod = ng.module("eventoModule");
    mod.constant("eventoContext", "eventos");
    mod.constant("clientesContext", "api/clientes");
    mod.controller('eventoDetailCtrl', ['$scope', '$http', 'eventoContext', '$state','clientesContext','$filter',
        function ($scope, $http, eventoContext, $state,clientesContext,$filter) {
            /**
             * @ngdoc controller
             * @name eventos.controller:eventoDetailCtrl
             * @description
             * Definición de un controlador auxiliar del módulo Eventos. 
             * Se crea el controlador con el cual se manejan las vistas de detalle
             * del módulo.
             * @param {Object} $scope Referencia injectada al Scope definida para este
             * controlador, el scope es el objeto que contiene las variables o 
             * funciones que se definen en este controlador y que son utilizadas 
             * desde el HTML.
             * @param {Object} $http Objeto injectado para la manejar consultas HTTP
             * @param {Object} eventoContext Constante injectada que contiene la ruta
             * donde se encuentra el API de Eventos en el Backend.
             * @param {Object} $state Dependencia injectada en la que se recibe el 
             * estado actual de la navegación definida en el módulo.
             * *@param {Object} clientesContext Constante injectada que contiene la ruta
             * donde se encuentra el API de Clientes en el Backend.
             */
            if (($state.params.eventoId !== undefined) && ($state.params.eventoId !== null)) {
                 /**
                 * @ngdoc function
                 * @name getEventoID
                 * @methodOf eventos.controller:                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      eventoDetailCtrl
                 * @description
                 * Esta función utiliza el protocolo HTTP para obtener el recurso 
                 * donde se encuentra el evento por ID en formato JSON.
                 * @param {String} URL Dirección donde se encuentra el recurso
                 * del evento o API donde se puede consultar.
                 */
                $http.get(clientesContext+ '/' + $state.params.clienteId + '/' +eventoContext).then(function (response) {
                    $scope.eventosRecords = response.data;
                    $scope.currentEvento = $filter('filter')($scope.eventosRecords, {id: $state.params.eventoId}, true)[0];
                    $scope.pagosRecords=response.data.pagos;
                    $scope.contratosRecords=response.data.contratos;
                    
                });
            }
        }
    ]);
}
)(window.angular);